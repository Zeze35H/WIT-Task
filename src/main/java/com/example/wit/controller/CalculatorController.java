package com.example.wit.controller;

import com.example.wit.model.CalculatorMessage;
import com.example.wit.model.ResponseMessage;
import com.example.wit.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    private final KafkaProducerService kafkaProducerService;
    private final ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory;

    @Autowired
    public CalculatorController(KafkaProducerService kafkaProducerService,
                                ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory) {
        this.kafkaProducerService = kafkaProducerService;
        this.kafkaListenerContainerFactory = kafkaListenerContainerFactory;
    }

    @GetMapping("/sum")
    public BigDecimal sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws ExecutionException, InterruptedException {
        String requestId = UUID.randomUUID().toString();
        CalculatorMessage message = new CalculatorMessage(a, b, requestId, "sum-response");

        CompletableFuture<ResponseMessage> future = new CompletableFuture<>();

        setupListener(message.getResponseTopic(), future, requestId);
        kafkaProducerService.sendMessage("sum", message);

        ResponseMessage response = future.get();
        System.out.println("Received response: " + response.toString());

        return response.getResult();
    }

    @GetMapping("/subtract")
    public BigDecimal subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws ExecutionException, InterruptedException {
        String requestId = UUID.randomUUID().toString();
        CalculatorMessage message = new CalculatorMessage(a, b, requestId, "subtract-response");

        CompletableFuture<ResponseMessage> future = new CompletableFuture<>();

        setupListener(message.getResponseTopic(), future, requestId);
        kafkaProducerService.sendMessage("subtract", message);

        ResponseMessage response = future.get();
        System.out.println("Received response: " + response.toString());

        return response.getResult();
    }

    @GetMapping("/multiply")
    public BigDecimal multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws ExecutionException, InterruptedException {
        String requestId = UUID.randomUUID().toString();
        CalculatorMessage message = new CalculatorMessage(a, b, requestId, "multiply-response");

        CompletableFuture<ResponseMessage> future = new CompletableFuture<>();

        setupListener(message.getResponseTopic(), future, requestId);
        kafkaProducerService.sendMessage("multiply", message);

        ResponseMessage response = future.get();
        System.out.println("Received response: " + response.toString());

        return response.getResult();
    }

    @GetMapping("/divide")
    public BigDecimal divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws ExecutionException, InterruptedException {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        String requestId = UUID.randomUUID().toString();
        CalculatorMessage message = new CalculatorMessage(a, b, requestId, "divide-response");

        CompletableFuture<ResponseMessage> future = new CompletableFuture<>();

        setupListener(message.getResponseTopic(), future, requestId);
        kafkaProducerService.sendMessage("divide", message);

        ResponseMessage response = future.get();
        System.out.println("Received response: " + response.toString());

        return response.getResult();
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
