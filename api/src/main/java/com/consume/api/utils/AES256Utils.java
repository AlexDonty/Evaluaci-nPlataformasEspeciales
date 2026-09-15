package com.consume.api.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AES256Utils {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;

    public static String decrypt( String encrypted, String secretKey) {

        try{

             byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            byte[] encryptedData =Base64.getDecoder().decode(encrypted);
            byte[] iv = Arrays.copyOfRange(encryptedData,0,IV_LENGTH_BYTE);
            byte[] cipherText = Arrays.copyOfRange( encryptedData,IV_LENGTH_BYTE,encryptedData.length);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT,iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,key,gcmParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(cipherText);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting: " + e.getMessage(), e);
        }
    }

    public static String encrypt( String encrypted, String secretKey) {

        try{
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            SecretKey key = new SecretKeySpec(keyBytes, "AES");

            byte[] iv = new byte[IV_LENGTH_BYTE];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);

            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT,iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,key,gcmParameterSpec);

            byte[] encryptedBytes =
                    cipher.doFinal(encrypted.getBytes(StandardCharsets.UTF_8));
            byte[] result = new byte[iv.length + encryptedBytes.length];

            System.arraycopy(iv,0,result, 0,iv.length);
            System.arraycopy(encryptedBytes,0,result,iv.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(result);

        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.getMessage(), e);
        }
    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);

        SecretKey key = keyGenerator.generateKey();
        return key;
    }
}
