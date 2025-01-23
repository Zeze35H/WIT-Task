package com.example.wit.model;

import java.math.BigDecimal;
import java.util.UUID;

public class CalculatorMessage {

    private BigDecimal a;
    private BigDecimal b;
    private String requestId;
    private String responseTopic;

    public CalculatorMessage() {
        this.a = new BigDecimal(1);
        this.b = new BigDecimal(1);
        this.requestId = UUID.randomUUID().toString();
        this.responseTopic = "default-response";
    }

    public CalculatorMessage(BigDecimal a, BigDecimal b, String requestId, String responseTopic) {
        this.a = a;
        this.b = b;
        this.requestId = requestId;
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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResponseTopic() {
        return responseTopic;
    }

    public void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public String toString() {
        return "CalculatorMessage(a=" + a + ", b=" + b + ", requestId=" + requestId + ", responseTopic=" + responseTopic + ")";
    }
}
