package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.ResultantFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ResultantFileRepository extends JpaRepository<ResultantFileEntity, String> {
    @Query(value = "SELECT * FROM resultant_file_info u WHERE u.id = :id order by u.timestamp ASC ", nativeQuery = true)
    List<ResultantFileEntity> findResultantFilesById(String id);
}

