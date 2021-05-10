package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.TargetFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetCSVFileRepository extends JpaRepository<TargetFileEntity, Integer> {
}

