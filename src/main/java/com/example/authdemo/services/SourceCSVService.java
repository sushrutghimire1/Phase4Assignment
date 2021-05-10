package com.example.authdemo.services;

import com.example.authdemo.repository.SourceCSVFileRepository;
import com.example.authdemo.repository.entities.SourceFileEntity;
import com.example.authdemo.util.SourceCSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SourceCSVService {
    @Autowired
    SourceCSVFileRepository repository;

    public void save(MultipartFile file) {
        try {
            List<SourceFileEntity> tutorials = SourceCSVHelper.csvToTargetFile(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<SourceFileEntity> getAllTutorials() {
        return repository.findAll();
    }
}
