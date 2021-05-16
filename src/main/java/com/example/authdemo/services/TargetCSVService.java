package com.example.authdemo.services;

import com.example.authdemo.repository.TargetFileRepository;
import com.example.authdemo.repository.entities.FileEntity;
import com.example.authdemo.repository.entities.SourceFileEntity;
import com.example.authdemo.repository.entities.TargetFileEntity;
import com.example.authdemo.util.TargetCSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TargetCSVService {

    private final TargetFileRepository repository;

    @Autowired
    public TargetCSVService(TargetFileRepository repository) {
        this.repository = repository;
    }

    public void save(String username, MultipartFile file) {
        try {
            List<TargetFileEntity> targetFileEntities = TargetCSVHelper.csvToTargetFile(username, file.getInputStream());
            repository.deleteByUsername(username);
            repository.saveAll(targetFileEntities);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<FileEntity> getFileEntities(String username) {
        List<TargetFileEntity> sourceFileEntities = new ArrayList<>(this.getAll(username));
        List<FileEntity> resultant = new ArrayList<>();
        sourceFileEntities.forEach(fileEntity -> {
            if (fileEntity.getSourceFileEntity() == null) {
                resultant.add(fileEntity);
            }
        });
        return resultant;
    }

    public List<TargetFileEntity> getAll(String username) {
        return repository.findByUsername(username);
    }
}
