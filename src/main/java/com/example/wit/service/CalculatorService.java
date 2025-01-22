package com.example.wit.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculatorService {

    public BigDecimal sum(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a.divide(b, 10, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        CalculatorService service = new CalculatorService();
        System.out.println(service.sum(new BigDecimal("1.5"), new BigDecimal("2.3"))); // 3.8
    }

}

