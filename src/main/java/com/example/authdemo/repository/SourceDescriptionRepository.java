package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.SourceDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SourceDescriptionRepository extends JpaRepository<SourceDescriptionEntity, Integer> {
    void deleteByUsername(String username);

    List<SourceDescriptionEntity> findByUsername(String username);
}

