package com.example.authdemo.services;

import com.example.authdemo.repository.SourceFileRepository;
import com.example.authdemo.repository.entities.FileEntity;
import com.example.authdemo.repository.entities.SourceFileEntity;
import com.example.authdemo.util.SourceCSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SourceCSVService {

    private final SourceFileRepository repository;

    @Autowired
    public SourceCSVService(SourceFileRepository repository) {
        this.repository = repository;
    }

    public void save(String username, MultipartFile file) {
        try {
            List<SourceFileEntity> fileEntities = SourceCSVHelper.csvToTargetFile(username, file.getInputStream());
            repository.deleteByUsername(username);
            repository.saveAll(fileEntities);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ArrayList<SourceFileEntity> getMatchingFileEntities(String username) {
        List<SourceFileEntity> sourceFileEntities = new ArrayList<>(this.getAll(username));
        ArrayList<SourceFileEntity> fileEntities = new ArrayList<>();
        sourceFileEntities.forEach(fileEntity -> {
            if (fileEntity.getTargetFileEntity() != null)
                fileEntities.add(fileEntity);
        });
        return fileEntities;
    }

    public List<FileEntity> getMismatchedFileEntities(String username) {
        List<SourceFileEntity> sourceFileEntities = new ArrayList<>(this.getAll(username));
        List<FileEntity> resultant = new ArrayList<>();
        sourceFileEntities.forEach(fileEntity -> {
            if (fileEntity.getTargetFileEntity() == null) {
                resultant.add(fileEntity);
            }
        });
        return resultant;
    }

    public List<SourceFileEntity> getAll(String username) {
        return repository.findByUsername(username);
    }
}
