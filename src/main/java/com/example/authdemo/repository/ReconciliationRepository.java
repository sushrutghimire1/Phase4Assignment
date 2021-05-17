package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.ReconciliationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReconciliationRepository extends JpaRepository<ReconciliationEntity, String> {

    List<ReconciliationEntity> findByUsername(String username);
}

