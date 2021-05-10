package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.SourceFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceCSVFileRepository extends JpaRepository<SourceFileEntity, Integer> {
}

