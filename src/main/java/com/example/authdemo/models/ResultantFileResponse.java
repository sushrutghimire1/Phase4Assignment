package com.example.authdemo.models;

import java.util.List;

public class ResultantFileResponse {
    List<FileInfo> matching;
    List<FileInfo> missing;
    List<FileInfo> mismatching;

    public List<FileInfo> getMatching() {
        return matching;
    }

    public void setMatching(List<FileInfo> matching) {
        this.matching = matching;
    }

    public List<FileInfo> getMissing() {
        return missing;
    }

    public void setMissing(List<FileInfo> missing) {
        this.missing = missing;
    }

    public List<FileInfo> getMismatching() {
        return mismatching;
    }

    public void setMismatching(List<FileInfo> mismatching) {
        this.mismatching = mismatching;
    }
}
