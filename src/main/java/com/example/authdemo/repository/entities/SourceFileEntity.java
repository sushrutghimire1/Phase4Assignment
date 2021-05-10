package com.example.authdemo.repository.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "source_file")
public class SourceFileEntity {
    @Id
    private String transactionId;
    private double amount;
    private String currency;
    private Date valueDate;

    public SourceFileEntity(String transactionId, double amount, String currency, Date valueDate) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.valueDate = valueDate;
    }

    public SourceFileEntity() {

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

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }
}
