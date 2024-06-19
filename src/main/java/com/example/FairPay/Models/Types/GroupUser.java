package com.example.FairPay.Models.Types;

public class GroupUser {
    private String groupId;
    private float userBalance;

    public GroupUser(String groupId, float userBalance) {
        this.groupId = groupId;
        this.userBalance = userBalance;
    }

    public GroupUser() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public float getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(float userBalance) {
        this.userBalance = userBalance;
    }
}
