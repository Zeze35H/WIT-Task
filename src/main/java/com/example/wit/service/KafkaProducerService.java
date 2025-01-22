package com.example.wit.service;

import com.example.wit.model.CalculatorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, CalculatorMessage> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, CalculatorMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String operation, CalculatorMessage message) {
        kafkaTemplate.send(operation, message);
    }
}

