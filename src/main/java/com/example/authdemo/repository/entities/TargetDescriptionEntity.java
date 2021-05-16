package com.example.authdemo.repository.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "target_file_desc")
public class TargetDescriptionEntity {
    @Id
    @Column(name = "target_name")
    private String sourceName;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "username")
    private String username;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
