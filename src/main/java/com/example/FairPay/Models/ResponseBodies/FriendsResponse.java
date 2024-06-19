package com.example.FairPay.Models.ResponseBodies;

import com.example.FairPay.Models.Types.EveryGroupUsers;
import com.example.FairPay.Models.Types.Suggestion;

import java.util.List;

public class FriendsResponse {
    List<EveryGroupUsers> users;
    List<Suggestion> suggestions;
    List<String> requests;
    List<String> friends;

    public FriendsResponse(List<EveryGroupUsers> users, List<Suggestion> suggestions, List<String> requests, List<String> friends) {
        this.users = users;
        this.suggestions = suggestions;
        this.requests = requests;
        this.friends = friends;
    }

    public FriendsResponse() {
    }

    public List<EveryGroupUsers> getUsers() {
        return users;
    }

    public void setUsers(List<EveryGroupUsers> users) {
        this.users = users;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public List<String> getRequests() {
        return requests;
    }

    public void setRequests(List<String> requests) {
        this.requests = requests;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
