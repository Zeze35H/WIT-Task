package com.example.wit.controller;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    @GetMapping("/sum")
    public BigDecimal sum(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        // TODO: Call CalculatorService
        return a.add(b);
    }

    @GetMapping("/subtract")
    public BigDecimal subtract(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        // TODO: Call CalculatorService
        return a.subtract(b);
    }

    @GetMapping("/multiply")
    public BigDecimal multiply(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        // TODO: Call CalculatorService
        return a.multiply(b);
    }

    @GetMapping("/divide")
    public BigDecimal divide(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        // TODO: Call CalculatorService
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a.divide(b, 10, RoundingMode.HALF_UP);

    }
}
