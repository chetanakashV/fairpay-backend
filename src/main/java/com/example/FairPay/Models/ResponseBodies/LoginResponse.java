package com.example.FairPay.Models.ResponseBodies;

import com.example.FairPay.Models.DB.User;

public class LoginResponse {
    private User user;

    private DefaultResponse response;

    public LoginResponse(User user, DefaultResponse response) {
        this.user = user;
        this.response = response;
    }

    public LoginResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DefaultResponse getResponse() {
        return response;
    }

    public void setResponse(DefaultResponse response) {
        this.response = response;
    }
}
