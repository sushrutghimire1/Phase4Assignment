package com.example.authdemo.services;

import com.example.authdemo.repository.SourceFileRepository;
import com.example.authdemo.repository.entities.SourceFileEntity;
import com.example.authdemo.util.SourceJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class SourceJsonService {

    private final SourceFileRepository repository;

    @Autowired
    public SourceJsonService(SourceFileRepository repository) {
        this.repository = repository;
    }

    public void save(String username, MultipartFile file) {
        try {
            List<SourceFileEntity> fileEntities = SourceJsonHelper.jsonToSourceFile(username, file.getInputStream());
            repository.deleteByUsername(username);
            if (fileEntities == null)
                throw new IOException("fail to store json data: ");
            repository.saveAll(fileEntities);
        } catch (IOException e) {
            throw new RuntimeException("fail to store json data: " + e.getMessage());
        }
    }

    public List<SourceFileEntity> getAll(String username) {
        return repository.findByUsername(username);
    }
}
