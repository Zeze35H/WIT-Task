package com.example.wit.controller;

import com.example.wit.model.CalculatorMessage;
import com.example.wit.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    private final KafkaProducerService kafkaProducerService;

    public CalculatorController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/sum")
    public BigDecimal sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculatorMessage message = new CalculatorMessage(a, b);
        kafkaProducerService.sendMessage("sum", message);
        return a.add(b);
    }

    @GetMapping("/subtract")
    public BigDecimal subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculatorMessage message = new CalculatorMessage(a, b);
        kafkaProducerService.sendMessage("subtract", message);
        return a.subtract(b);
    }

    @GetMapping("/multiply")
    public BigDecimal multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        CalculatorMessage message = new CalculatorMessage(a, b);
        kafkaProducerService.sendMessage("multiply", message);
        return a.multiply(b);
    }

    @GetMapping("/divide")
    public BigDecimal divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        CalculatorMessage message = new CalculatorMessage(a, b);
        kafkaProducerService.sendMessage("divide", message);
        return a.divide(b, 10, RoundingMode.HALF_UP);
    }
}
