package com.example.FairPay.Models.DB;

import com.example.FairPay.Models.Types.Participant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.mail.Part;
import java.util.Date;
import java.util.List;

@Document(collection = "expenses")
public class Expense {
    @Id
    private String _id;

    @Field
    private String description;

    @Field
    private String contributorId;

    @Field
    private Date transactionDate;

    @Field
    private String groupId;

    @Field
    private  String createdBy;

    @Field
    private Float totalAmount;

    @Field
   private List<Participant> participants;

    public Expense(String _id, String description, String contributorId, Date transactionDate, String groupId, Float totalAmount, List<Participant> participants) {
        this._id = _id;
        this.description = description;
        this.contributorId = contributorId;
        this.transactionDate = transactionDate;
        this.groupId = groupId;
        this.totalAmount = totalAmount;
        this.participants = participants;
    }

    public Expense() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContributorId() {
        return contributorId;
    }

    public void setContributorId(String contributorId) {
        this.contributorId = contributorId;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
