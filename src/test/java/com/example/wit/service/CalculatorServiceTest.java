package com.example.wit.service;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testSum() {
        assertEquals(new BigDecimal("5.0"), calculatorService.sum(new BigDecimal("2.0"), new BigDecimal("3.0")));
    }

    @Test
    void testSubtract() {
        assertEquals(new BigDecimal("1.0"), calculatorService.subtract(new BigDecimal("4.0"), new BigDecimal("3.0")));
    }

    @Test
    void testMultiply() {
        assertEquals(0, calculatorService.multiply(new BigDecimal("2.0"), new BigDecimal("3.0")).compareTo(new BigDecimal("6.0")));
    }

    @Test
    void testDivide() {
        assertEquals(0, calculatorService.divide(new BigDecimal("4.0"), new BigDecimal("2.0")).compareTo(new BigDecimal("2.0")));
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calculatorService.divide(new BigDecimal("4.0"), BigDecimal.ZERO));
    }
}
