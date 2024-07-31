package com.cslprocerbackend.controller;

import com.cslprocerbackend.model.Documento;
import com.cslprocerbackend.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping
    public ResponseEntity<Documento> uploadDocumento(@RequestBody Documento documento) {
        return ResponseEntity.ok(documentoService.saveDocumento(documento));
    }

    @GetMapping
    public ResponseEntity<List<Documento>> getAllDocumentos() {
        return ResponseEntity.ok(documentoService.getAllDocumentos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        documentoService.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }
}
