package com.example.FairPay.Models.RequestBodies.Expenses;


import com.example.FairPay.Models.Types.Participant;

import java.util.Date;
import java.util.List;

public class CreateExpenseBody {

    private String contributorId;
    private String description;
    private Float totalAmount;
    private Date transactionDate;
    private List<Participant> participants;
    private String groupId;
    private String createdBy;

    public CreateExpenseBody(String contributorId, String description, Float totalAmount, Date transactionDate, List<Participant> participants, String groupId, String createdBy) {
        this.contributorId = contributorId;
        this.description = description;
        this.totalAmount = totalAmount;
        this.transactionDate = transactionDate;
        this.participants = participants;
        this.groupId = groupId;
        this.createdBy = createdBy;
    }

    public CreateExpenseBody() {
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getContributorId() {
        return contributorId;
    }

    public void setContributorId(String contributorId) {
        this.contributorId = contributorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
