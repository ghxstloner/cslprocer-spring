package com.cslprocerbackend.service;

import com.cslprocerbackend.model.InformeAuditoria;
import com.cslprocerbackend.repository.InformeAuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformeAuditoriaService {

    @Autowired
    private InformeAuditoriaRepository informeAuditoriaRepository;

    public InformeAuditoria saveInforme(InformeAuditoria informe) {
        return informeAuditoriaRepository.save(informe);
    }

    public List<InformeAuditoria> getAllInformes() {
        return informeAuditoriaRepository.findAll();
    }

    public void deleteInforme(Long id) {
        informeAuditoriaRepository.deleteById(id);
    }
}