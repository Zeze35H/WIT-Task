package com.example.wit.model;

import java.math.BigDecimal;

public class CalculatorMessage {

    private BigDecimal a;
    private BigDecimal b;
    private String responseTopic;

    public CalculatorMessage() {
        this.a = new BigDecimal(1);
        this.b = new BigDecimal(1);
        this.responseTopic = "default-response";
    }

    public CalculatorMessage(BigDecimal a, BigDecimal b, String responseTopic) {
        this.a = a;
        this.b = b;
        this.responseTopic = responseTopic;
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

    public String getResponseTopic() {
        return responseTopic;
    }

    public void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public String toString() {
        return "CalculatorMessage(a=" + a + ", b=" + b + ", responseTopic=" + responseTopic + ")";
    }
}
