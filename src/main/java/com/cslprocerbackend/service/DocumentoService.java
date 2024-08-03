package com.cslprocerbackend.service;

import com.cslprocerbackend.model.Documento;
import com.cslprocerbackend.model.Usuario;
import com.cslprocerbackend.repository.DocumentoRepository;
import com.cslprocerbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * Servicio para gestionar operaciones relacionadas con documentos.
 */
@Service
public class DocumentoService {

    private final Path root = Paths.get("uploads");
    private final DocumentoRepository documentoRepository;
    private final UsuarioRepository usuarioRepository;
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 5; // 5MB

    @Autowired
    public DocumentoService(DocumentoRepository documentoRepository, UsuarioRepository usuarioRepository) {
        this.documentoRepository = documentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Guarda un nuevo documento en la base de datos y en el sistema de archivos.
     *
     * @param encryptedFile El contenido del archivo cifrado.
     * @param usuario El usuario al que pertenece el documento.
     * @return El nombre del archivo guardado.
     * @throws IOException Si ocurre un error al guardar el archivo.
     */
    public String saveDocumento(String encryptedFile, Usuario usuario) throws IOException {
        // Generación de un ID único para el documento
        String uuid = UUID.randomUUID().toString();

        // Construir la ruta completa del archivo a guardar
        String fileName = uuid + ".enc";
        Path filePath = root.resolve(fileName);

        // Crear el objeto Documento y asignar los valores
        Documento documento = new Documento();
        documento.setOriginalFileName("encrypted_file.enc");
        documento.setFileName(fileName);
        documento.setFilePath(filePath.toString());
        documento.setUsuario(usuario);
        documento.setEncryptedContent(encryptedFile);

        // Guardar el documento en la base de datos
        documentoRepository.save(documento);

        // Guardar el archivo en el sistema de archivos
        Files.write(filePath, encryptedFile.getBytes());

        return fileName;
    }

    /**
     * Obtiene un documento por su ID.
     *
     * @param id El ID del documento.
     * @return El documento encontrado.
     */
    public Documento getDocumentoById(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
    }

    /**
     * Elimina un documento de la base de datos y su archivo asociado.
     *
     * @param id El ID del documento a eliminar.
     * @throws IOException Si ocurre un error al eliminar el archivo.
     */
    public void deleteDocumento(Long id) throws IOException {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        // Elimina el archivo físico
        Path filePath = Paths.get(documento.getFilePath());
        Files.deleteIfExists(filePath);

        // Elimina el registro del documento en la base de datos
        documentoRepository.delete(documento);
    }

    /**
     * Obtiene el contenido de un documento como un array de bytes.
     *
     * @param id El ID del documento.
     * @return El contenido del documento.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public byte[] getDocumentoBytes(Long id) throws IOException {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        Path filePath = Paths.get(documento.getFilePath());
        if (!Files.exists(filePath)) {
            throw new IOException("El archivo no existe en el sistema de archivos");
        }

        return Files.readAllBytes(filePath);
    }

    /**
     * Obtiene una lista de documentos por ID de usuario.
     *
     * @param usuario El ID del usuario.
     * @return La lista de documentos.
     */
    public List<Documento> getDocumentosByUser(Long usuario) {
        return documentoRepository.findByUsuarioId(usuario);
    }

    /**
     * Actualiza un documento.
     *
     * @param id El ID del documento.
     * @param file El nuevo archivo.
     * @param newFileName El nuevo nombre del archivo.
     * @return El documento actualizado.
     * @throws IOException Si ocurre un error al actualizar el archivo.
     */
    public Documento updateDocumento(Long id, MultipartFile file, String newFileName) throws IOException {
        Documento documento = documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        if (file != null && !file.isEmpty()) {
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException("Archivo excede el tamaño máximo permitido");
            }

            // Elimina el archivo físico existente
            Path existingFilePath = Paths.get(documento.getFilePath());
            Files.deleteIfExists(existingFilePath);

            // Guardar el nuevo archivo en el sistema de archivos
            String uuid = UUID.randomUUID().toString();
            String extension = file.getOriginalFilename() != null ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")) : "";
            String fileName = uuid + extension;
            Path newFilePath = root.resolve(fileName);
            Files.copy(file.getInputStream(), newFilePath);

            documento.setFileName(fileName);
            documento.setFilePath(newFilePath.toString());
            documento.setOriginalFileName(file.getOriginalFilename());
        }

        if (newFileName != null && !newFileName.isEmpty()) {
            documento.setOriginalFileName(newFileName);
        }

        return documentoRepository.save(documento);
    }
}
