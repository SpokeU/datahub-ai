package dev.omyshko.datahubai.connections.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.omyshko.datahubai.connections.exception.EncryptionException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ConnectionEncryptionService {
    
    private final SecretKeySpec secretKey;
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";

    public ConnectionEncryptionService(@Value("${datahub.encryption.key}") String encryptionKey) {
        // Ensure key is exactly 32 bytes for AES-256
        byte[] key = encryptionKey.getBytes(StandardCharsets.UTF_8);
        if (key.length != 32) {
            throw new IllegalArgumentException("Encryption key must be exactly 32 bytes (256 bits)");
        }
        this.secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
    }

    public Map<String, String> encryptConnectionDetails(Map<String, String> details) {
        Map<String, String> encryptedDetails = new HashMap<>();
        
        details.forEach((key, value) -> {
            if (value != null) {
                encryptedDetails.put(key, encrypt(value));
            }
        });
        
        return encryptedDetails;
    }

    public Map<String, String> decryptConnectionDetails(Map<String, String> encryptedDetails) {
        Map<String, String> decryptedDetails = new HashMap<>();
        
        encryptedDetails.forEach((key, value) -> {
            if (value != null) {
                decryptedDetails.put(key, decrypt(value));
            }
        });
        
        return decryptedDetails;
    }

    private String encrypt(String value) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new EncryptionException("Failed to encrypt value", e);
        }
    }

    private String decrypt(String encryptedValue) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Failed to decrypt value: {}", encryptedValue, e);
            throw new EncryptionException("Failed to decrypt value: {}" , e);
        }
    }
}