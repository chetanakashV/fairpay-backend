package com.example.FairPay.Models.RequestBodies.Groups;

public class AddParticipantBody {
    private String userId;

    private String groupId;

    public AddParticipantBody(String userId, String groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public AddParticipantBody() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
