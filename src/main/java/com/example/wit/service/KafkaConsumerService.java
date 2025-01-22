package com.example.wit.service;
import com.example.wit.model.CalculatorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaConsumerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CalculatorService calculatorService;

    @Autowired
    public KafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate, CalculatorService calculatorService) {
        this.kafkaTemplate = kafkaTemplate;
        this.calculatorService = calculatorService;
    }

    @KafkaListener(topics = "sum", groupId = "operations")
    public void listenSum(CalculatorMessage message) {
        System.out.println("Sum!");
        System.out.println("Received message: " + message);
        BigDecimal result = calculatorService.sum(message.getA(), message.getB());
        System.out.println("Result: " + result.toString());
        kafkaTemplate.send(message.getResponseTopic(), String.valueOf(result));
    }

    @KafkaListener(topics = "subtract", groupId = "operations")
    public void listenSubtract(CalculatorMessage message) {
        System.out.println("Subtract!");
        System.out.println("Received message: " + message);
        BigDecimal result = calculatorService.subtract(message.getA(), message.getB());
        System.out.println("Result: " + result.toString());
        kafkaTemplate.send(message.getResponseTopic(), String.valueOf(result));
    }

    @KafkaListener(topics = "multiply", groupId = "operations")
    public void listenMultiply(CalculatorMessage message) {
        System.out.println("Multiply!");
        System.out.println("Received message: " + message);
        BigDecimal result = calculatorService.multiply(message.getA(), message.getB());
        System.out.println("Result: " + result.toString());
        kafkaTemplate.send(message.getResponseTopic(), String.valueOf(result));
    }

    @KafkaListener(topics = "divide", groupId = "operations")
    public void listenDivide(CalculatorMessage message) {
        System.out.println("Divide!");
        System.out.println("Received message: " + message);
        BigDecimal result = calculatorService.divide(message.getA(), message.getB());
        System.out.println("Result: " + result.toString());
        kafkaTemplate.send(message.getResponseTopic(), String.valueOf(result));
    }
}
