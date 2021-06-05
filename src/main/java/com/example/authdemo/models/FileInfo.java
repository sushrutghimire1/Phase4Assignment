package com.example.authdemo.models;


public class FileInfo {

    private String transactionId;
    private double amount;
    private String currency;
    private String valueDate;

    public FileInfo() {
    }

    public FileInfo(String transactionId, double amount, String currency, String valueDate) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.valueDate = valueDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }
}
