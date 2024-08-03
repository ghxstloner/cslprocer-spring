package com.cslprocerbackend.controller;

import com.cslprocerbackend.model.Documento;
import com.cslprocerbackend.model.Usuario;
import com.cslprocerbackend.service.DocumentoService;
import com.cslprocerbackend.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controlador REST para gestionar operaciones relacionadas con documentos.
 */
@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private EncryptionService encryptionService;

    /**
     * Sube un documento.
     * @param file El archivo a subir.
     * @param usuario El usuario al que pertenece el documento.
     * @return Respuesta con el nombre del archivo subido.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocumento(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("usuario") Usuario usuario) {
        try {
            byte[] fileBytes = file.getBytes();
            String encryptedFile = encryptionService.encrypt(fileBytes);
            String fileName = documentoService.saveDocumento(encryptedFile, usuario);
            return ResponseEntity.ok("Documento subido exitosamente: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al subir el documento: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir el documento: " + e.getMessage());
        }
    }

    /**
     * Obtiene un documento por su ID.
     * @param id El ID del documento.
     * @return Respuesta con el contenido del documento.
     */
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getDocumento(@PathVariable Long id) {
        try {
            Documento documento = documentoService.getDocumentoById(id);
            byte[] decryptedFile = encryptionService.decrypt(documento.getEncryptedContent());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment().filename(documento.getOriginalFileName()).build());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity.ok().headers(headers).body(decryptedFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Actualiza un documento.
     * @param id El ID del documento.
     * @param file El nuevo archivo (opcional).
     * @param newFileName El nuevo nombre del archivo (opcional).
     * @return Respuesta con el documento actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Documento> updateDocumento(@PathVariable Long id,
                                                     @RequestParam(value = "file", required = false) MultipartFile file,
                                                     @RequestParam(value = "newFileName", required = false) String newFileName) {
        try {
            Documento updatedDocumento = documentoService.updateDocumento(id, file, newFileName);
            return ResponseEntity.ok(updatedDocumento);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Elimina un documento por su ID.
     * @param id El ID del documento.
     * @return Respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        try {
            documentoService.deleteDocumento(id);
            return ResponseEntity.noContent().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtiene documentos por ID de usuario.
     * @param usuarioId El ID del usuario.
     * @return Respuesta con la lista de documentos.
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Documento>> getDocumentoByUser(@PathVariable Long usuarioId) {
        List<Documento> documentos = documentoService.getDocumentosByUser(usuarioId);
        return ResponseEntity.ok(documentos);
    }
}
