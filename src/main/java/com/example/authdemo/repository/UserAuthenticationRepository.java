package com.example.authdemo.repository;

import com.example.authdemo.repository.entities.AuthCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAuthenticationRepository extends JpaRepository<AuthCredentialsEntity, Integer> {
    @Query(value = "SELECT * FROM auth_credentials u WHERE u.username = :username", nativeQuery = true)
    AuthCredentialsEntity findClientByUsername(@Param("username") String username);
}

