package com.cslprocerbackend.service;

import com.cslprocerbackend.model.Documento;
import com.cslprocerbackend.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    public Documento saveDocumento(Documento documento) {
        return documentoRepository.save(documento);
    }

    public List<Documento> getAllDocumentos() {
        return documentoRepository.findAll();
    }

    public void deleteDocumento(Long id) {
        documentoRepository.deleteById(id);
    }
}
