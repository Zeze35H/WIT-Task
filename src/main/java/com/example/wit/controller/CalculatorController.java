package com.example.wit.controller;

import com.example.wit.model.CalculatorMessage;
import com.example.wit.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
        CalculatorMessage message = new CalculatorMessage(a, b, "sum-response");
        CompletableFuture<BigDecimal> future = new CompletableFuture<>();

        setupListener(message.getResponseTopic(), future);
        kafkaProducerService.sendMessage("sum", message);

        return future.get();
    }

    @GetMapping("/subtract")
    public BigDecimal subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws ExecutionException, InterruptedException {
        CalculatorMessage message = new CalculatorMessage(a, b, "subtract-response");
        CompletableFuture<BigDecimal> future = new CompletableFuture<>();

        setupListener(message.getResponseTopic(), future);
        kafkaProducerService.sendMessage("subtract", message);

        return future.get();
    }

    @GetMapping("/multiply")
    public BigDecimal multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws ExecutionException, InterruptedException {
        CalculatorMessage message = new CalculatorMessage(a, b, "multiply-response");
        CompletableFuture<BigDecimal> future = new CompletableFuture<>();

        setupListener(message.getResponseTopic(), future);
        kafkaProducerService.sendMessage("multiply", message);

        return future.get();
    }

    @GetMapping("/divide")
    public BigDecimal divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) throws ExecutionException, InterruptedException {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        CalculatorMessage message = new CalculatorMessage(a, b, "divide-response");
        CompletableFuture<BigDecimal> future = new CompletableFuture<>();

        setupListener(message.getResponseTopic(), future);
        kafkaProducerService.sendMessage("divide", message);

        return future.get();
    }

    private void setupListener(String topic, CompletableFuture<BigDecimal> future) {
        // Create a temporary Kafka listener
        ContainerProperties containerProps = new ContainerProperties(topic);
        containerProps.setMessageListener((MessageListener<String, String>) record -> {
            future.complete(new BigDecimal(record.value()));
        });

        KafkaMessageListenerContainer<String, String> container = new KafkaMessageListenerContainer<>(
                kafkaListenerContainerFactory.getConsumerFactory(), containerProps);
        container.start();

        // Stop the container after the message is received
        future.whenComplete((result, ex) -> container.stop());
    }
}
