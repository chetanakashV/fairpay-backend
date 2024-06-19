package com.example.FairPay.Models.RequestBodies.Friends;

public class FriendRequest {
    private String senderId;
    private String receiverId;
    private String type;

    public FriendRequest(String senderId, String receiverId, String type) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.type = type;
    }

    public FriendRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
