package com.example.FairPay.Models.RequestBodies.Groups;

public class RemoveParticipantBody {
    private String userId;

    private String groupId;

    public RemoveParticipantBody(String userId, String groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public RemoveParticipantBody() {
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
