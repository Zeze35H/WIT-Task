package com.example.wit.model;

import java.math.BigDecimal;

public class CalculatorMessage {
    private BigDecimal a;
    private BigDecimal b;

    public CalculatorMessage() {
        this.a = new BigDecimal(1);
        this.b = new BigDecimal(1);
    }

    public CalculatorMessage(BigDecimal a, BigDecimal b) {
        this.a = a;
        this.b = b;
    }

    public BigDecimal getA() {
        return a;
    }

    public void setA(BigDecimal a) {
        this.a = a;
    }

    public BigDecimal getB() {
        return b;
    }

    public void setB(BigDecimal b) {
        this.b = b;
    }

    public String toString() {
        return "CalculatorMessage(a=" + a + ", b=" + b + ")";
    }
}
