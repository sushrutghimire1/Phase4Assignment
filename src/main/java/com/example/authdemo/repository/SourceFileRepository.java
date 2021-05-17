package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.SourceFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SourceFileRepository extends JpaRepository<SourceFileEntity, String> {
    void deleteByUsername(String username);

    List<SourceFileEntity> findByUsername(String username);
}

