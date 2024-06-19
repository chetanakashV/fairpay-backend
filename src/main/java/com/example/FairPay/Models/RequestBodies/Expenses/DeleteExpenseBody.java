package com.example.FairPay.Models.RequestBodies.Expenses;

public class DeleteExpenseBody {
    private String expenseId;

    public DeleteExpenseBody(String expenseId) {
        this.expenseId = expenseId;
    }

    public DeleteExpenseBody() {
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }
}
