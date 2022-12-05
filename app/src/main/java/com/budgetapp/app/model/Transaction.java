package com.budgetapp.app.model;

public class Transaction {
    private int id;
    private String text;
    private String amount;
    private String transaction_date;
    private String category;

    public Transaction() {

    }

    public Transaction(int id, String text, String amount, String transaction_date, String category) {
        this.id = id;
        this.text = text;
        this.amount = amount;
        this.transaction_date = transaction_date;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
