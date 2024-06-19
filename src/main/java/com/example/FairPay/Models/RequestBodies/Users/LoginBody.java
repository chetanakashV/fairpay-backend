package com.example.FairPay.Models.RequestBodies.Users;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginBody {
    private String userEmail;
    private String password;

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
