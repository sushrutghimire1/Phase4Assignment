package com.example.authdemo.repository.entities;

import com.example.authdemo.models.FileDescriptionInfo;
import com.example.authdemo.models.FileInfo;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity(name = "file_info")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class FileEntity {
    @Id
    @Column(name = "id")
    String id;

    @Column(name = "username")
    String username;

    @Column(name = "target_file")
    @Type(type = "jsonb")
    List<FileInfo> targetFile;

    @Column(name = "target_description")
    @Type(type = "jsonb")
    FileDescriptionInfo targetDescription;

    @Column(name = "source_file")
    @Type(type = "jsonb")
    List<FileInfo> sourceFile;

    @Column(name = "source_description")
    @Type(type = "jsonb")
    FileDescriptionInfo sourceDescription;

    @Column(name = "timestamp")
    String time;

    public FileEntity() {
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

    public List<FileInfo> getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(List<FileInfo> targetFile) {
        this.targetFile = targetFile;
    }

    public FileDescriptionInfo getTargetDescription() {
        return targetDescription;
    }

    public void setTargetDescription(FileDescriptionInfo targetDescription) {
        this.targetDescription = targetDescription;
    }

    public List<FileInfo> getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(List<FileInfo> sourceFile) {
        this.sourceFile = sourceFile;
    }

    public FileDescriptionInfo getSourceDescription() {
        return sourceDescription;
    }

    public void setSourceDescription(FileDescriptionInfo sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
