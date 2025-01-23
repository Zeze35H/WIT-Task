package com.example.wit.service;

import com.example.wit.model.CalculatorMessage;
import com.example.wit.model.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class KafkaProducerServiceTest {

    private final KafkaTemplate<String, CalculatorMessage> kafkaMessageTemplate = mock(KafkaTemplate.class);
    private final KafkaTemplate<String, ResponseMessage> kafkaResponseTemplate = mock(KafkaTemplate.class);
    private final KafkaProducerService kafkaProducerService = new KafkaProducerService(kafkaMessageTemplate, kafkaResponseTemplate);

    @Test
    void testSendCalculatorMessage() {
        kafkaProducerService.sendCalculatorMessage("sum", new BigDecimal("2.0"), new BigDecimal("3.0"), "123", "sum-response");
        verify(kafkaMessageTemplate, times(1)).send(eq("sum"), any(CalculatorMessage.class));
    }

    @Test
    void testSendResponseMessage() {
        kafkaProducerService.sendResponseMessage("sum-response", new BigDecimal("5.0"), "123");
        verify(kafkaResponseTemplate, times(1)).send(eq("sum-response"), any(ResponseMessage.class));
    }
}
