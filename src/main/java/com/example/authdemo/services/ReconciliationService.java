package com.example.authdemo.services;

import com.example.authdemo.models.ReconciliationActions;
import com.example.authdemo.repository.ReconciliationRepository;
import com.example.authdemo.repository.entities.ReconciliationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReconciliationService {
    private final ReconciliationRepository reconciliationRepository;

    @Autowired
    public ReconciliationService(ReconciliationRepository reconciliationRepository) {
        this.reconciliationRepository = reconciliationRepository;
    }

    public List<ReconciliationEntity> getAllReconciliations(String username) {
        return reconciliationRepository.findByUsername(username);
    }

    public void updateReconciliation(String username) {
        ReconciliationEntity reconciliationEntity = new ReconciliationEntity();
        reconciliationEntity.setUsername(username);
        reconciliationEntity.setAction(ReconciliationActions.LOGIN.toString());
        reconciliationEntity.setTimestamp(LocalDateTime.now().toString());
        this.reconciliationRepository.save(reconciliationEntity);
    }
}
