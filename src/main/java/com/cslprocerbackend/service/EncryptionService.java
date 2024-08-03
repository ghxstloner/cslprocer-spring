package com.cslprocerbackend.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Servicio para manejar la encriptación y desencriptación de datos utilizando el algoritmo AES en modo GCM.
 */
@Service
public class EncryptionService {

    private static final String AES = "AES";
    private static final String AES_GCM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12; // Longitud del IV (Initial Vector) para GCM
    private static final int GCM_TAG_LENGTH = 16; // Longitud de la etiqueta para GCM

    private SecretKey secretKey;

    /**
     * Constructor de EncryptionService. Genera una clave secreta para la encriptación.
     *
     * @throws Exception si ocurre un error al generar la clave.
     */
    public EncryptionService() throws Exception {
        this.secretKey = generateKey();
    }

    /**
     * Genera una clave secreta para el cifrado AES.
     *
     * @return la clave secreta generada.
     * @throws Exception si ocurre un error al generar la clave.
     */
    private SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(AES);
        keyGen.init(256); // Tamaño de la clave en bits
        return keyGen.generateKey();
    }

    /**
     * Encripta los datos proporcionados utilizando AES en modo GCM.
     *
     * @param data los datos a encriptar.
     * @return una cadena Base64 que representa los datos encriptados.
     * @throws Exception si ocurre un error durante la encriptación.
     */
    public String encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_GCM);
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv); // Genera un IV aleatorio
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        byte[] encryptedData = cipher.doFinal(data);
        byte[] encryptedIvAndData = new byte[GCM_IV_LENGTH + encryptedData.length];
        System.arraycopy(iv, 0, encryptedIvAndData, 0, GCM_IV_LENGTH);
        System.arraycopy(encryptedData, 0, encryptedIvAndData, GCM_IV_LENGTH, encryptedData.length);
        return Base64.getEncoder().encodeToString(encryptedIvAndData);
    }

    /**
     * Desencripta los datos proporcionados utilizando AES en modo GCM.
     *
     * @param encryptedData una cadena Base64 que representa los datos encriptados.
     * @return los datos desencriptados.
     * @throws Exception si ocurre un error durante la desencriptación.
     */
    public byte[] decrypt(String encryptedData) throws Exception {
        byte[] encryptedIvAndData = Base64.getDecoder().decode(encryptedData);
        byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(encryptedIvAndData, 0, iv, 0, iv.length);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        Cipher cipher = Cipher.getInstance(AES_GCM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        return cipher.doFinal(encryptedIvAndData, GCM_IV_LENGTH, encryptedIvAndData.length - GCM_IV_LENGTH);
    }
}
