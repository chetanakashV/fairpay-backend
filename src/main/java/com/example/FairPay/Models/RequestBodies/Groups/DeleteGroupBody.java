package com.example.FairPay.Models.RequestBodies.Groups;

public class DeleteGroupBody {
    private  String groupId;

    public DeleteGroupBody(String groupId) {
        this.groupId = groupId;
    }

    public DeleteGroupBody() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
