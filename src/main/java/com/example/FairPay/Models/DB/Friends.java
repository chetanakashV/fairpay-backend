package com.example.FairPay.Models.DB;

import com.example.FairPay.Models.Types.Suggestion;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "friends")
public class Friends {
    @Id
    private String _id;
    @Field
    private List<String> userFriends = new ArrayList<String>();
    @Field
    private List<Suggestion> userSuggestions = new ArrayList<Suggestion>();
    @Field
    private List<String> userRequests = new ArrayList<String>();

    public Friends(String _id, List<String> userFriends, List<Suggestion> userSuggestions, List<String> userRequests) {
        this._id = _id;
        this.userFriends = userFriends;
        this.userSuggestions = userSuggestions;
        this.userRequests = userRequests;
    }

    public Friends() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getUserFriends() {
        return userFriends;
    }

    public void setUserFriends(List<String> userFriends) {
        this.userFriends = userFriends;
    }

    public List<Suggestion> getUserSuggestions() {
        return userSuggestions;
    }

    public void setUserSuggestions(List<Suggestion> userSuggestions) {
        this.userSuggestions = userSuggestions;
    }

    public List<String> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(List<String> userRequests) {
        this.userRequests = userRequests;
    }
}
