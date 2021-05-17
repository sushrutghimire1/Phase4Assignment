package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.TargetDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TargetDescriptionRepository extends JpaRepository<TargetDescriptionEntity, String> {
    void deleteByUsername(String username);

    List<TargetDescriptionEntity> findByUsername(String username);
}

