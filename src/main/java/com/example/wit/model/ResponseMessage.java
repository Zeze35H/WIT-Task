package com.example.wit.model;

import java.math.BigDecimal;
import java.util.UUID;

public class ResponseMessage {

    private BigDecimal result;
    private String requestId;

    public ResponseMessage() {
        this.result = new BigDecimal(1);
        this.requestId = UUID.randomUUID().toString();
    }

    public ResponseMessage(BigDecimal result, String requestId) {
        this.result = result;
        this.requestId = requestId;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    public String toString() {
        return "ResponseMessage(result=" + result + ", requestId=" + requestId + ")";
    }
}
