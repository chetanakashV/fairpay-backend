package com.example.FairPay.Models.RequestBodies.Email;

public class VerifyBody {
    private String email;
    private Integer otp;

    public VerifyBody(String email, Integer otp) {
        this.email = email;
        this.otp = otp;
    }

    public VerifyBody() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
