package com.example.wit.service;
import com.example.wit.model.CalculatorMessage;
import com.example.wit.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaConsumerService {

    private final KafkaTemplate<String, ResponseMessage> kafkaTemplate;
    private final CalculatorService calculatorService;

    @Autowired
    public KafkaConsumerService(KafkaTemplate<String, ResponseMessage> kafkaTemplate, CalculatorService calculatorService) {
        this.kafkaTemplate = kafkaTemplate;
        this.calculatorService = calculatorService;
    }

    @KafkaListener(topics = "sum", groupId = "operations")
    public void listenSum(CalculatorMessage message) {
        System.out.println("Sum!");
        System.out.println("Received message: " + message);

        BigDecimal result = calculatorService.sum(message.getA(), message.getB());

        ResponseMessage response = new ResponseMessage(result, message.getRequestId());
        kafkaTemplate.send(message.getResponseTopic(), response);
    }

    @KafkaListener(topics = "subtract", groupId = "operations")
    public void listenSubtract(CalculatorMessage message) {
        System.out.println("Subtract!");
        System.out.println("Received message: " + message);

        BigDecimal result = calculatorService.subtract(message.getA(), message.getB());

        ResponseMessage response = new ResponseMessage(result, message.getRequestId());
        kafkaTemplate.send(message.getResponseTopic(), response);
    }

    @KafkaListener(topics = "multiply", groupId = "operations")
    public void listenMultiply(CalculatorMessage message) {
        System.out.println("Multiply!");
        System.out.println("Received message: " + message);

        BigDecimal result = calculatorService.multiply(message.getA(), message.getB());

        ResponseMessage response = new ResponseMessage(result, message.getRequestId());
        kafkaTemplate.send(message.getResponseTopic(), response);
    }

    @KafkaListener(topics = "divide", groupId = "operations")
    public void listenDivide(CalculatorMessage message) {
        System.out.println("Divide!");
        System.out.println("Received message: " + message);

        BigDecimal result = calculatorService.divide(message.getA(), message.getB());

        ResponseMessage response = new ResponseMessage(result, message.getRequestId());
        kafkaTemplate.send(message.getResponseTopic(), response);
    }
}
