package com.example.authdemo.repository.entities;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public abstract class FileEntity{
    @Id
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "amount")
    private double amount;
    @Column(name = "currency")
    private String currency;
    @Column(name = "value_date")
    private String valueDate;
    @Column(name = "username")
    private String username;

    public FileEntity(String transactionId, double amount, String currency, String valueDate) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.valueDate = valueDate;
    }

    public FileEntity() {

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
