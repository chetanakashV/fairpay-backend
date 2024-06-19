package com.example.FairPay.Models.Types;

public class Suggestion {
    private String userId;
    private boolean sent;

    public Suggestion(String userId, boolean sent) {
        this.userId = userId;
        this.sent = sent;
    }

    public Suggestion() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
