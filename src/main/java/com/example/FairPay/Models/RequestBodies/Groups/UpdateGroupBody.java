package com.example.FairPay.Models.RequestBodies.Groups;

import java.util.List;

public class UpdateGroupBody {
    private String groupId;
    private String groupName;
    private String groupType;
    private String groupDescription;
    private String groupPhoto;
    private String updatedBy;
    private List<String> newUsers;

    public UpdateGroupBody(String groupId, String groupName, String groupType, String groupDescription, String groupPhoto, List<String> newUsers) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
        this.groupDescription = groupDescription;
        this.groupPhoto = groupPhoto;
        this.newUsers = newUsers;
    }

    public UpdateGroupBody() {
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public List<String> getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(List<String> newUsers) {
        this.newUsers = newUsers;
    }
}
