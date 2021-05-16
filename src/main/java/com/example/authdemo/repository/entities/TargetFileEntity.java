package com.example.authdemo.repository.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "target_file")
public class TargetFileEntity extends FileEntity{

    public TargetFileEntity(String transactionId, double amount, String currency, String valueDate) {
        super(transactionId, amount, currency, valueDate);
    }

    public TargetFileEntity() {
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    private SourceFileEntity sourceFileEntity;

    public SourceFileEntity getSourceFileEntity() {
        return sourceFileEntity;
    }

    public void setSourceFileEntity(SourceFileEntity sourceFileEntity) {
        this.sourceFileEntity = sourceFileEntity;
    }
}
