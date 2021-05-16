package com.example.authdemo.models;

import com.example.authdemo.repository.entities.SourceDescriptionEntity;
import com.example.authdemo.repository.entities.TargetDescriptionEntity;

import javax.persistence.Column;

public class FileDescriptionResponse {
    private String sourceName;
    private String targetName;

    private String sourceFileType;
    private String targetFileType;

    private String sourceFileName;
    private String targetFileName;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getSourceFileType() {
        return sourceFileType;
    }

    public void setSourceFileType(String sourceFileType) {
        this.sourceFileType = sourceFileType;
    }

    public String getTargetFileType() {
        return targetFileType;
    }

    public void setTargetFileType(String targetFileType) {
        this.targetFileType = targetFileType;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }
}
