package com.example.authdemo.services;

import com.example.authdemo.repository.TargetCSVFileRepository;
import com.example.authdemo.repository.entities.TargetFileEntity;
import com.example.authdemo.util.TargetCSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TargetCSVService {
    @Autowired
    TargetCSVFileRepository repository;

    public void save(MultipartFile file) {
        try {
            List<TargetFileEntity> tutorials = TargetCSVHelper.csvToTargetFile(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<TargetFileEntity> getAllTutorials() {
        return repository.findAll();
    }
}
