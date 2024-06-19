package com.example.FairPay.Models.Types;

public class Participant {
    private String userId;

    private Float balance;

    public Participant(String userId, Float balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Participant() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
