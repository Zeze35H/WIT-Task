package com.example.wit.service;

import static org.mockito.Mockito.verify;

import com.example.wit.service.KafkaProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducer;

    public KafkaProducerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage() {
        kafkaProducer.sendMessage("my-topic", "TestMessage");
        verify(kafkaTemplate).send("my-topic", "TestMessage");
    }
}
