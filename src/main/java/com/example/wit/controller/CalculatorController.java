package com.example.wit.controller;

import com.example.wit.model.ResponseMessage;
import com.example.wit.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    private final KafkaProducerService kafkaProducerService;
    private final ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory;

    @Autowired
    public CalculatorController(KafkaProducerService kafkaProducerService,
                                ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory) {
        this.kafkaProducerService = kafkaProducerService;
        this.kafkaListenerContainerFactory = kafkaListenerContainerFactory;
    }

    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return processOperation(a, b, "sum", "sum-response");
    }

    @GetMapping("/subtract")
    public ResponseEntity<BigDecimal> subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return processOperation(a, b, "subtract", "subtract-response");
    }

    @GetMapping("/multiply")
    public ResponseEntity<BigDecimal> multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return processOperation(a, b, "multiply", "multiply-response");
    }

    @GetMapping("/divide")
    public ResponseEntity<BigDecimal> divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return processOperation(a, b, "divide", "divide-response");
    }

    private ResponseEntity<BigDecimal> processOperation(BigDecimal a, BigDecimal b, String operation, String responseTopic) {
        if (a == null || b == null || operation == null || operation.isEmpty() || responseTopic == null || responseTopic.isEmpty()) {
            throw new IllegalArgumentException("Argument cannot be null or empty.");
        }

        String requestId = UUID.randomUUID().toString();
        CompletableFuture<ResponseMessage> future = new CompletableFuture<>();
        setupListener(responseTopic, future, requestId);

        kafkaProducerService.sendCalculatorMessage(operation, a, b, requestId, responseTopic);

        try {
            ResponseMessage response = future.get();
            logger.info("[{}] Received response: {}", operation, response);
            return ResponseEntity.ok()
                    .header("X-Request-ID", response.getRequestId())
                    .body(response.getResult());
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error processing operation [{}]: {}", operation, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing request");
        }
    }

    private void setupListener(String topic, CompletableFuture<ResponseMessage> future, String requestId) {
        // Create a temporary Kafka listener
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setMessageListener((MessageListener<String, ResponseMessage>) record -> {
            ResponseMessage response = record.value();
            if (response.getRequestId().equals(requestId)) {
                future.complete(record.value());
            }
        });

        KafkaMessageListenerContainer<String, String> container = new KafkaMessageListenerContainer<>(
                kafkaListenerContainerFactory.getConsumerFactory(), containerProps);
        container.start();

        // Stop the container after the message is received
        future.whenComplete((result, ex) -> container.stop());
    }
}
