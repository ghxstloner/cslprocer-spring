package com.cslprocerbackend.controller;

import com.cslprocerbackend.model.Mensaje;
import com.cslprocerbackend.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    @PostMapping
    public ResponseEntity<Mensaje> sendMensaje(@RequestBody Mensaje mensaje) {
        return ResponseEntity.ok(mensajeService.saveMensaje(mensaje));
    }

    @GetMapping
    public ResponseEntity<List<Mensaje>> getAllMensajes() {
        return ResponseEntity.ok(mensajeService.getAllMensajes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long id) {
        mensajeService.deleteMensaje(id);
        return ResponseEntity.noContent().build();
    }
}
