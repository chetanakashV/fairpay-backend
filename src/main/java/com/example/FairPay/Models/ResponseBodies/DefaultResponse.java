package com.example.FairPay.Models.ResponseBodies;

public class DefaultResponse {
    private Integer status;
    private String message;

    public DefaultResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public DefaultResponse() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
