package com.example.wit.service;

import com.example.wit.model.CalculatorMessage;
import com.example.wit.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

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
        logger.info("[{}]: Sending message:{}", topic, message);
        kafkaMessageTemplate.send(topic, message);
    }

    public void sendResponseMessage(String topic, BigDecimal result, String requestId) {
        ResponseMessage response = new ResponseMessage(result, requestId);
        logger.info("[{}]: Sending response:{}", topic, response);
        kafkaResponseTemplate.send(topic, response);
    }
}

