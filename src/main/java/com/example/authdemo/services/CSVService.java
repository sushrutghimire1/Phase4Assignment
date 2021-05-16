package com.example.authdemo.services;

import com.example.authdemo.repository.entities.FileEntity;
import com.example.authdemo.repository.entities.SourceFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CSVService {
    void save(MultipartFile file);
}
