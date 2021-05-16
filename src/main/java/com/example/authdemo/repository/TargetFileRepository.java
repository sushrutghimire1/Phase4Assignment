package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.TargetFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TargetFileRepository extends JpaRepository<TargetFileEntity, Integer> {
    void deleteByUsername(String username);

    List<TargetFileEntity> findByUsername(String username);
}

