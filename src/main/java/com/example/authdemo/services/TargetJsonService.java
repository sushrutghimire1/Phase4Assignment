package com.example.authdemo.services;

import com.example.authdemo.repository.TargetFileRepository;
import com.example.authdemo.repository.entities.TargetFileEntity;
import com.example.authdemo.util.TargetJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class TargetJsonService {

    private final TargetFileRepository repository;

    @Autowired
    public TargetJsonService(TargetFileRepository repository) {
        this.repository = repository;
    }

    public void save(String username, MultipartFile file) {
        try {
            List<TargetFileEntity> targetFileEntities = TargetJsonHelper.jsonToTargetFile(username, file.getInputStream());
            if (targetFileEntities == null)
                throw new IOException("fail to store json data: ");
            repository.deleteByUsername(username);
            repository.saveAll(targetFileEntities);
        } catch (IOException e) {
            throw new RuntimeException("fail to store json data: " + e.getMessage());
        }
    }

    public List<TargetFileEntity> getAll(String username) {
        return repository.findByUsername(username);
    }
}
