package com.example.wit.service;
import com.example.wit.model.CalculatorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaConsumerService {

    private final KafkaProducerService kafkaProducerService;
    private final CalculatorService calculatorService;

    @Autowired
    public KafkaConsumerService(KafkaProducerService kafkaProducerService,
                                CalculatorService calculatorService) {
        this.kafkaProducerService = kafkaProducerService;
        this.calculatorService = calculatorService;
    }

    @KafkaListener(topics = "sum", groupId = "operations")
    public void listenSum(CalculatorMessage message) {
        System.out.println("[sum] Received message: " + message);
        BigDecimal result = calculatorService.sum(message.getA(), message.getB());
        kafkaProducerService.sendResponseMessage(message.getResponseTopic(), result, message.getRequestId());
    }

    @KafkaListener(topics = "subtract", groupId = "operations")
    public void listenSubtract(CalculatorMessage message) {
        System.out.println("[subtract] Received message: " + message);
        BigDecimal result = calculatorService.subtract(message.getA(), message.getB());
        kafkaProducerService.sendResponseMessage(message.getResponseTopic(), result, message.getRequestId());
    }

    @KafkaListener(topics = "multiply", groupId = "operations")
    public void listenMultiply(CalculatorMessage message) {
        System.out.println("[multiply] Received message: " + message);
        BigDecimal result = calculatorService.multiply(message.getA(), message.getB());
        kafkaProducerService.sendResponseMessage(message.getResponseTopic(), result, message.getRequestId());
    }

    @KafkaListener(topics = "divide", groupId = "operations")
    public void listenDivide(CalculatorMessage message) {
        System.out.println("[divide] Received message: " + message);
        BigDecimal result = calculatorService.divide(message.getA(), message.getB());
        kafkaProducerService.sendResponseMessage(message.getResponseTopic(), result, message.getRequestId());
    }
}
