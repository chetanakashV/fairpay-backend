package com.example.FairPay.Models.Types;

public class GroupShare {
    private String groupName;
    private String groupPhoto;
    private Float groupBalance;

    public GroupShare(String groupName, String groupPhoto, Float groupBalance) {
        this.groupName = groupName;
        this.groupPhoto = groupPhoto;
        this.groupBalance = groupBalance;
    }

    public GroupShare() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public Float getGroupBalance() {
        return groupBalance;
    }

    public void setGroupBalance(Float groupBalance) {
        this.groupBalance = groupBalance;
    }
}
