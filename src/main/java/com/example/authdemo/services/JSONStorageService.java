package com.example.authdemo.services;

import org.springframework.web.multipart.MultipartFile;

public interface JSONStorageService {
    void save(String username, MultipartFile file);
}
