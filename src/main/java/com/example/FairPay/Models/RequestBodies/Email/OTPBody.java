package com.example.FairPay.Models.RequestBodies.Email;

public class OTPBody {
    private String email;
    private String type;
    private String org;

    public OTPBody(String email, String type, String org, String subject) {
        this.email = email;
        this.type = type;
        this.org = org;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

}
