package com.example.FairPay.Models.Types;

public class EveryGroupUsers {
    private String userName;
    private String userEmail;
    private String userId;
    private String userPhoto;

    public EveryGroupUsers(String userName, String userEmail, String userId, String userPhoto) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
        this.userPhoto = userPhoto;
    }

    public EveryGroupUsers() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }
}
