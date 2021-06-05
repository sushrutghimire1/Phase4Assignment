package com.example.authdemo.controllers;

import com.example.authdemo.repository.entities.ReconciliationEntity;
import com.example.authdemo.services.ReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class ReconciliationController {
    private final ReconciliationService reconciliationService;

    @Autowired
    public ReconciliationController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    @GetMapping("/reconciliations")
    List<ReconciliationEntity> getReconciliationDetails(Principal principal) {
        return reconciliationService.getAllReconciliations(principal.getName());
    }
}
