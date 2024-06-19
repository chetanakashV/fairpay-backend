package com.example.FairPay.Models.DB;

public class Settings {

    private String _id;
    private boolean newGroup = true;
    private boolean updateGroup = false;
    private boolean newExpense = false;
    private boolean deleteExpense = false;
    private boolean sendRequest = true;
    private boolean acceptRequest = true;
    private boolean fairPayUpdates = true;
    private boolean monthlySummaries = false;

    public Settings(boolean newGroup, boolean updateGroup, boolean newExpense, boolean deleteExpense, boolean sendRequest, boolean acceptRequest, boolean fairPayUpdates, boolean monthlySummaries) {
        this.newGroup = newGroup;
        this.updateGroup = updateGroup;
        this.newExpense = newExpense;
        this.deleteExpense = deleteExpense;
        this.sendRequest = sendRequest;
        this.acceptRequest = acceptRequest;
        this.fairPayUpdates = fairPayUpdates;
        this.monthlySummaries = monthlySummaries;
    }

    public Settings(String _id, boolean newGroup, boolean updateGroup, boolean newExpense, boolean deleteExpense, boolean sendRequest, boolean acceptRequest, boolean fairPayUpdates, boolean monthlySummaries) {
        this._id = _id;
        this.newGroup = newGroup;
        this.updateGroup = updateGroup;
        this.newExpense = newExpense;
        this.deleteExpense = deleteExpense;
        this.sendRequest = sendRequest;
        this.acceptRequest = acceptRequest;
        this.fairPayUpdates = fairPayUpdates;
        this.monthlySummaries = monthlySummaries;
    }

    public Settings() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isNewGroup() {
        return newGroup;
    }

    public void setNewGroup(boolean newGroup) {
        this.newGroup = newGroup;
    }

    public boolean isUpdateGroup() {
        return updateGroup;
    }

    public void setUpdateGroup(boolean updateGroup) {
        this.updateGroup = updateGroup;
    }

    public boolean isNewExpense() {
        return newExpense;
    }

    public void setNewExpense(boolean newExpense) {
        this.newExpense = newExpense;
    }

    public boolean isDeleteExpense() {
        return deleteExpense;
    }

    public void setDeleteExpense(boolean deleteExpense) {
        this.deleteExpense = deleteExpense;
    }

    public boolean isSendRequest() {
        return sendRequest;
    }

    public void setSendRequest(boolean sendRequest) {
        this.sendRequest = sendRequest;
    }

    public boolean isAcceptRequest() {
        return acceptRequest;
    }

    public void setAcceptRequest(boolean acceptRequest) {
        this.acceptRequest = acceptRequest;
    }

    public boolean isFairPayUpdates() {
        return fairPayUpdates;
    }

    public void setFairPayUpdates(boolean fairPayUpdates) {
        this.fairPayUpdates = fairPayUpdates;
    }

    public boolean isMonthlySummaries() {
        return monthlySummaries;
    }

    public void setMonthlySummaries(boolean monthlySummaries) {
        this.monthlySummaries = monthlySummaries;
    }
}
