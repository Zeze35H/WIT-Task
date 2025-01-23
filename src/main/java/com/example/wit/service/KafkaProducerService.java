package com.example.wit.service;

import com.example.wit.model.CalculatorMessage;
import com.example.wit.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, CalculatorMessage> kafkaMessageTemplate;
    private final KafkaTemplate<String, ResponseMessage> kafkaResponseTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, CalculatorMessage> kafkaMessageTemplate,
                                KafkaTemplate<String, ResponseMessage> kafkaResponseTemplate) {
        this.kafkaMessageTemplate = kafkaMessageTemplate;
        this.kafkaResponseTemplate = kafkaResponseTemplate;
    }

    public void sendCalculatorMessage(String topic, BigDecimal a, BigDecimal b, String requestId, String responseTopic) {
        CalculatorMessage message = new CalculatorMessage(a, b, requestId, responseTopic);
        System.out.printf("[" + topic + "]: Sending message:" + message);
        kafkaMessageTemplate.send(topic, message);
    }

    public void sendResponseMessage(String topic, BigDecimal result, String requestId) {
        ResponseMessage response = new ResponseMessage(result, requestId);
        System.out.printf("[" + topic + "]: Sending response:" + response);
        kafkaResponseTemplate.send(topic, response);
    }
}

