package com.example.authdemo.services;

import com.example.authdemo.models.ResultantFileResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveAndCompareFiles(String username, MultipartFile sourceFile, String sourceDescription, MultipartFile targetFile, String targetDescription) throws JsonProcessingException;

    ResultantFileResponse getResultantFileInfo(String username, String id);
}
