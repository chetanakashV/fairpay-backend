package com.example.FairPay.Models.DB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    String _id; 
    
    @Field
    String userName; 
    
    @Field
    String userMobile; 
    
    @Field
    String userEmail; 
    
    @Field
    String password;

    @Field
    String userDetailsId;

    @Field
    String userSettingsId;

    @Field
    String userFriendsId;

    @Field
    String imageUrl;


    public User() {
    }

    public User(String _id, String userName, String userFriendsId, String userMobile, String userEmail, String password, String userDetailsId, String imageUrl, String userSettingsId) {
        this._id = _id;
        this.userName = userName;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.password = password;
        this.userDetailsId = userDetailsId;
        this.userFriendsId = userFriendsId;
        this.imageUrl = imageUrl;
        this.userSettingsId = userSettingsId;
    }

    public String getUserFriendsId() {
        return userFriendsId;
    }

    public void setUserFriendsId(String userFriendsId) {
        this.userFriendsId = userFriendsId;
    }

    public String getUserDetailsId() {
        return userDetailsId;
    }

    public String getUserSettingsId() {
        return userSettingsId;
    }

    public void setUserSettingsId(String userSettingsId) {
        this.userSettingsId = userSettingsId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUserDetailsId(String userDetailsId) {
        this.userDetailsId = userDetailsId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
