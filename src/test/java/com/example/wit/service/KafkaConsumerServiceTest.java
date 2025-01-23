package com.example.wit.service;

import com.example.wit.model.CalculatorMessage;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;

class KafkaConsumerServiceTest {

    private final KafkaProducerService kafkaProducerService = mock(KafkaProducerService.class);
    private final CalculatorService calculatorService = mock(CalculatorService.class);
    private final KafkaConsumerService kafkaConsumerService = new KafkaConsumerService(kafkaProducerService, calculatorService);

    @Test
    void testListenSum() {
        CalculatorMessage message = new CalculatorMessage(new BigDecimal("2.0"), new BigDecimal("3.0"), "123", "sum-response");
        when(calculatorService.sum(message.getA(), message.getB())).thenReturn(new BigDecimal("5.0"));

        kafkaConsumerService.listenSum(message);

        verify(kafkaProducerService, times(1)).sendResponseMessage("sum-response", new BigDecimal("5.0"), "123");
    }
}
