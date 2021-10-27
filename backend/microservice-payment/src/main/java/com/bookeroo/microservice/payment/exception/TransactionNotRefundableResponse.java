package com.bookeroo.microservice.payment.exception;

public class TransactionNotRefundableResponse {

    private String id;

    public TransactionNotRefundableResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
