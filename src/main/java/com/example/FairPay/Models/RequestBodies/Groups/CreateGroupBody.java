package com.example.FairPay.Models.RequestBodies.Groups;

import java.util.List;

public class CreateGroupBody {
    private String groupName;

    private String groupDescription;

    private String groupType;

    private String groupPhoto;

    private String createdBy;

    private List<String> groupMembers;

    public CreateGroupBody(String groupName, String groupDescription, String groupType, String groupPhoto, String createdBy, List<String> groupMembers) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupType = groupType;
        this.groupPhoto = groupPhoto;
        this.createdBy = createdBy;
        this.groupMembers = groupMembers;
    }

    public CreateGroupBody() {
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
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

    public List<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<String> groupMembers) {
        this.groupMembers = groupMembers;
    }
}
