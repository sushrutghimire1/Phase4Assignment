package com.example.authdemo.services;

import com.example.authdemo.models.ReconciliationActions;
import com.example.authdemo.repository.ReconciliationRepository;
import com.example.authdemo.repository.entities.ReconciliationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    public void updateLoginReconciliation(String username) {
        ReconciliationEntity reconciliationEntity = new ReconciliationEntity();
        reconciliationEntity.setId(UUID.randomUUID().toString().substring(0, 10));
        reconciliationEntity.setUsername(username);
        reconciliationEntity.setAction(ReconciliationActions.LOGIN.toString());
        reconciliationEntity.setTimestamp(LocalDateTime.now().toString());
        this.reconciliationRepository.save(reconciliationEntity);
    }

    public void updateCompareReconciliation(String username, String id, String time) {
        ReconciliationEntity reconciliationEntity = new ReconciliationEntity();
        reconciliationEntity.setId(id);
        reconciliationEntity.setUsername(username);
        reconciliationEntity.setAction(ReconciliationActions.COMPARE.toString());
        reconciliationEntity.setTimestamp(time);
        this.reconciliationRepository.save(reconciliationEntity);
    }
}
