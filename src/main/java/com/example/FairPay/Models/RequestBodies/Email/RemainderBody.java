package com.example.FairPay.Models.RequestBodies.Email;

public class RemainderBody {
    private String senderName;
    private String receiverName;
    private String groupName;
    private String receiverEmail;

    public RemainderBody(String senderName, String receiverName, String groupName, String receiverEmail) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.groupName = groupName;
        this.receiverEmail = receiverEmail;
    }

    public RemainderBody() {
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
