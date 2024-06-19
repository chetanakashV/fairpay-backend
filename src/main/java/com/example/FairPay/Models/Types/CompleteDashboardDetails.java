package com.example.FairPay.Models.Types;

import java.util.List;

public class CompleteDashboardDetails {
    private Float totalBalance;
    private Float amountOwed;
    private Float amountYouOwe;
    private List<GroupShare> groups;

    public CompleteDashboardDetails(Float totalBalance, Float amountOwed, Float amountYouOwe, List<GroupShare> groups) {
        this.totalBalance = totalBalance;
        this.amountOwed = amountOwed;
        this.amountYouOwe = amountYouOwe;
        this.groups = groups;
    }

    public CompleteDashboardDetails(){}

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

    public List<GroupShare> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupShare> groups) {
        this.groups = groups;
    }
}
