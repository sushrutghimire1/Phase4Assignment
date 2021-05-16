package com.example.authdemo.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "source_file")
public class SourceFileEntity extends FileEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    private TargetFileEntity targetFileEntity;

    public SourceFileEntity(String transactionId, double amount, String currency, String valueDate) {
        super(transactionId, amount, currency, valueDate);
    }

    public SourceFileEntity() {
    }

    public TargetFileEntity getTargetFileEntity() {
        return targetFileEntity;
    }

    public void setTargetFileEntity(TargetFileEntity targetFileEntity) {
        this.targetFileEntity = targetFileEntity;
    }
}
