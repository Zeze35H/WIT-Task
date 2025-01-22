package com.example.wit.service;
import com.example.wit.model.CalculatorMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    static CalculatorService calculator = new CalculatorService();

    @KafkaListener(topics = "sum", groupId = "operations")
    public void listenSum(CalculatorMessage message) {
        System.out.println("Sum!");
        System.out.println(calculator.sum(message.getA(), message.getB()));
        System.out.println("Received message: " + message);
    }

    @KafkaListener(topics = "subtract", groupId = "operations")
    public void listenSubtract(CalculatorMessage message) {
        System.out.println("Subtract!");
        System.out.println(calculator.subtract(message.getA(), message.getB()));
        System.out.println("Received message: " + message);
    }

    @KafkaListener(topics = "multiply", groupId = "operations")
    public void listenMultiply(CalculatorMessage message) {
        System.out.println("Multiply!");
        System.out.println(calculator.multiply(message.getA(), message.getB()));
        System.out.println("Received message: " + message);
    }

    @KafkaListener(topics = "divide", groupId = "operations")
    public void listenDivide(CalculatorMessage message) {
        System.out.println("Divide!");
        System.out.println(calculator.divide(message.getA(), message.getB()));
        System.out.println("Received message: " + message);
    }
}
