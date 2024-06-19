package com.example.FairPay.Models.Types;

import java.util.ArrayList;
import java.util.List;

public class GroupUserBalance {
    private String userId;
    private Float balance;
    private List<String> items = new ArrayList<String>();

    public GroupUserBalance(String userId, Float balance, List<String> items) {
        this.userId = userId;
        this.balance = balance;
        this.items = items;
    }

    public GroupUserBalance(String userId, Float balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public GroupUserBalance() {
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

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
