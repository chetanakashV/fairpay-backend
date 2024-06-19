package com.example.FairPay.Models.ResponseBodies;

public class SocketResponse {

    private DefaultResponse response;

    private String messageType;

    private String body ;

    public SocketResponse(DefaultResponse response, String messageType, String body) {
        this.response = response;
        this.messageType = messageType;
        this.body = body;
    }

    public SocketResponse() {
    }

    public DefaultResponse getResponse() {
        return response;
    }

    public void setResponse(DefaultResponse response) {
        this.response = response;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
