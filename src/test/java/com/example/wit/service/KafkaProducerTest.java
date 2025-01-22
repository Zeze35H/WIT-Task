package com.example.wit.service;

import static org.mockito.Mockito.verify;

import com.example.wit.model.CalculatorMessage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, CalculatorMessage> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducer;

    public KafkaProducerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage() {
        CalculatorMessage calculatorMessage = new CalculatorMessage();
        kafkaProducer.sendMessage("sum", calculatorMessage);
        verify(kafkaTemplate).send("sum", calculatorMessage);
    }
}
