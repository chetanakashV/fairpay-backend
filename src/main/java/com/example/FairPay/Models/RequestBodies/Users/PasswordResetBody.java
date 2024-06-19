package com.example.FairPay.Models.RequestBodies.Users;

public class PasswordResetBody {
    private String userEmail;
    private String password;

    public PasswordResetBody(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

    public PasswordResetBody() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
