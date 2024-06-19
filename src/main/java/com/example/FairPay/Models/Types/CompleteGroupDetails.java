package com.example.FairPay.Models.Types;

public class CompleteGroupDetails {
    private String groupId;
    private String groupName;
    private String groupDescription;
    private String groupType;
    private String groupPhoto;
    private String groupMembersBody;
    private String ExpensesBody;
    private String groupUsersBody;

    public CompleteGroupDetails(String groupId, String groupName, String groupDescription, String groupType, String groupPhoto, String groupMembersBody, String expensesBody, String groupUsersBody) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupType = groupType;
        this.groupPhoto = groupPhoto;
        this.groupMembersBody = groupMembersBody;
        ExpensesBody = expensesBody;
        this.groupUsersBody = groupUsersBody;
    }

    public String getGroupUsersBody() {
        return groupUsersBody;
    }

    public void setGroupUsersBody(String groupUsersBody) {
        this.groupUsersBody = groupUsersBody;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public CompleteGroupDetails() {
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

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupMembersBody() {
        return groupMembersBody;
    }

    public void setGroupMembersBody(String groupMembersBody) {
        this.groupMembersBody = groupMembersBody;
    }

    public String getExpensesBody() {
        return ExpensesBody;
    }

    public void setExpensesBody(String expensesBody) {
        ExpensesBody = expensesBody;
    }
}
