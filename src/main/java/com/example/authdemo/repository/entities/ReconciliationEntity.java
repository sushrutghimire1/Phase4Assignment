package com.example.authdemo.repository.entities;


import javax.persistence.*;

@Entity(name = "reconciliation")
public class ReconciliationEntity {
    @Id
    String id;
    @Column(name = "username")
    private String username;
    @Column(name = "action")
    private String action;
    @Column(name = "timestamp")
    private String timestamp;

    public ReconciliationEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
