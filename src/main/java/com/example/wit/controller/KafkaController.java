package com.example.wit.controller;
import com.example.wit.model.CalculatorMessage;
import com.example.wit.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String operation, @RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        if (!List.of("sum", "subtract", "multiply", "divide").contains(operation)) {
            throw new IllegalArgumentException("Invalid operation: " + operation + ".\n" +
                    "operation must be one of 'sum', 'subtract', 'multiply', 'divide'.");
        }
        kafkaProducerService.sendCalculatorMessage(operation, a, b, UUID.randomUUID().toString(), operation+"-response");
        return "CalculatorMessage sent to operation [" + operation + "]";
    }
}