package com.cslprocerbackend.controller;

import com.cslprocerbackend.model.InformeAuditoria;
import com.cslprocerbackend.service.InformeAuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/informes")
public class InformeAuditoriaController {

    @Autowired
    private InformeAuditoriaService informeAuditoriaService;

    @PostMapping
    public ResponseEntity<InformeAuditoria> createInforme(@RequestBody InformeAuditoria informe) {
        return ResponseEntity.ok(informeAuditoriaService.saveInforme(informe));
    }

    @GetMapping
    public ResponseEntity<List<InformeAuditoria>> getAllInformes() {
        return ResponseEntity.ok(informeAuditoriaService.getAllInformes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInforme(@PathVariable Long id) {
        informeAuditoriaService.deleteInforme(id);
        return ResponseEntity.noContent().build();
    }
}
