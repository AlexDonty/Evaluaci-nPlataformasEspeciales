package com.consume.save.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class Utils {

    public static final String ESTATUS = "Aprobada";
    public static final String ESTATUS_C = "Cancelada";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public static String generarReferencia() {
        int numero = RANDOM.nextInt(1_000_000);
        return String.format("%06d", numero);
    }

    public static String hash(String password) {
        return encoder.encode(password);
    }

    public static boolean matches(String password, String passwordSave) {
        return encoder.matches(password, passwordSave);
    }
}
