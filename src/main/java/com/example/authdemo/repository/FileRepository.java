package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface FileRepository extends JpaRepository<FileEntity, String> {
}

