package com.example.authdemo.services;

import org.springframework.web.multipart.MultipartFile;

public interface CSVStorageService {
    void save(String username, MultipartFile file);
}
