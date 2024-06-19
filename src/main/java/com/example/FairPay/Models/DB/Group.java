package com.example.FairPay.Models.DB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "groups")
public class Group {
    @Id
    private  String _id;

    @Field
    private String groupName;

    @Field
    private String groupDescription;

    @Field
    private String groupType;

    @Field
    private String createdBy;

    @Field
    private String groupMembersId;

    @Field
    private List<String> groupExpenses = new ArrayList<String>();

    @Field
    private String groupPhoto;

    public Group(String _id, String groupName, String groupDescription, String groupType, String createdBy, String groupMembersId, List<String> groupExpenses, String groupPhoto) {
        this._id = _id;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupType = groupType;
        this.createdBy = createdBy;
        this.groupMembersId = groupMembersId;
        this.groupExpenses = groupExpenses;
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

    public Group() {
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public String getGroupMembersId() {
        return groupMembersId;
    }

    public void setGroupMembersId(String groupMembersId) {
        this.groupMembersId = groupMembersId;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getGroupExpenses() {
        return groupExpenses;
    }

    public void setGroupExpenses(List<String> groupExpenses) {
        this.groupExpenses = groupExpenses;
    }
}
