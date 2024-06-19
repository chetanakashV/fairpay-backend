package com.example.FairPay.Models.DB;

import com.example.FairPay.Models.Types.GroupUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "userDetails")
public class UserDetails{
    @Id
    private String _id;

    @Field
    private Float totalBalance = 0F;

    @Field
    private Float amountOwed = 0F;

    @Field
    private Float amountYouOwe = 0F;

    @Field
    private List<GroupUser> userGroups = new ArrayList<GroupUser>();

    @Field
    private List<String> userActivities = new ArrayList<String>();

    public UserDetails(String _id, Float totalBalance, Float amountOwed, Float amountYouOwe, List<GroupUser> userGroups, List<String> userActivities) {
        this._id = _id;
        this.totalBalance = totalBalance;
        this.amountOwed = amountOwed;
        this.amountYouOwe = amountYouOwe;
        this.userGroups = userGroups;
        this.userActivities = userActivities;
    }

    public UserDetails() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getUserActivities() {
        return userActivities;
    }

    public void setUserActivities(List<String> userActivities) {
        this.userActivities = userActivities;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public Float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Float totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Float getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(Float amountOwed) {
        this.amountOwed = amountOwed;
    }

    public Float getAmountYouOwe() {
        return amountYouOwe;
    }

    public void setAmountYouOwe(Float amountYouOwe) {
        this.amountYouOwe = amountYouOwe;
    }

    public List<GroupUser> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(List<GroupUser> userGroups) {
        this.userGroups = userGroups;
    }


}
