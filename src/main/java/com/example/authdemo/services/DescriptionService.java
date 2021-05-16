package com.example.authdemo.services;

import com.example.authdemo.models.FileDescriptionResponse;
import com.example.authdemo.repository.SourceDescriptionRepository;
import com.example.authdemo.repository.TargetDescriptionRepository;
import com.example.authdemo.repository.entities.SourceDescriptionEntity;
import com.example.authdemo.repository.entities.TargetDescriptionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DescriptionService {

    private final TargetDescriptionRepository targetDescriptionRepository;
    private final SourceDescriptionRepository sourceDescriptionRepository;

    @Autowired
    public DescriptionService(TargetDescriptionRepository targetDescriptionRepository, SourceDescriptionRepository sourceDescriptionRepository) {
        this.targetDescriptionRepository = targetDescriptionRepository;
        this.sourceDescriptionRepository = sourceDescriptionRepository;
    }

    public SourceDescriptionEntity storeSourceDescription(String username, SourceDescriptionEntity entity) {
        this.sourceDescriptionRepository.deleteByUsername(username);
        entity.setUsername(username);
        return this.sourceDescriptionRepository.save(entity);
    }

    public SourceDescriptionEntity getSourceDescription(String username) {
        return this.sourceDescriptionRepository.findByUsername(username).get(0);
    }

    public TargetDescriptionEntity getTargetDescription(String username) {
        return this.targetDescriptionRepository.findByUsername(username).get(0);
    }

    public TargetDescriptionEntity storeTargetDescription(String username, TargetDescriptionEntity entity) {
        this.targetDescriptionRepository.deleteByUsername(username);
        entity.setUsername(username);
        return this.targetDescriptionRepository.save(entity);
    }

    public FileDescriptionResponse getFileDescriptionResponse(String username) {
        SourceDescriptionEntity sourceDescription = this.getSourceDescription(username);
        TargetDescriptionEntity targetDescription = this.getTargetDescription(username);
        FileDescriptionResponse fileDescriptionResponse = new FileDescriptionResponse();
        fileDescriptionResponse.setSourceFileName(sourceDescription.getFileName());
        fileDescriptionResponse.setSourceName(sourceDescription.getSourceName());
        fileDescriptionResponse.setSourceFileType(sourceDescription.getFileType());
        fileDescriptionResponse.setTargetFileName(targetDescription.getFileName());
        fileDescriptionResponse.setTargetName(targetDescription.getSourceName());
        fileDescriptionResponse.setTargetFileType(targetDescription.getFileType());
        return fileDescriptionResponse;
    }
}
