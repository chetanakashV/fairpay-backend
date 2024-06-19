package com.example.FairPay.Models.RequestBodies.Expenses;

public class GetBalancesBody {
    private String groupId;

    public GetBalancesBody(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
