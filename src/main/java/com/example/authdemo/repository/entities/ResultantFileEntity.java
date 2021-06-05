package com.example.authdemo.repository.entities;

import com.example.authdemo.models.FileInfo;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity(name = "resultant_file_info")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ResultantFileEntity {
    @Id
    @Column(name = "id")
    String id;

    @Column(name = "username")
    String username;

    @Column(name = "timestamp")
    String timestamp;

    @Column(name = "match")
    @Type(type = "jsonb")
    List<FileInfo> match;

    @Column(name = "mismatch")
    @Type(type = "jsonb")
    List<FileInfo> mismatch;

    @Column(name = "missing")
    @Type(type = "jsonb")
    List<FileInfo> missing;

    public ResultantFileEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FileInfo> getMatch() {
        return match;
    }

    public void setMatch(List<FileInfo> match) {
        this.match = match;
    }

    public List<FileInfo> getMismatch() {
        return mismatch;
    }

    public void setMismatch(List<FileInfo> mismatch) {
        this.mismatch = mismatch;
    }

    public List<FileInfo> getMissing() {
        return missing;
    }

    public void setMissing(List<FileInfo> missing) {
        this.missing = missing;
    }
}
