package com.example.FairPay.Services;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class PasswordUtils {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // Hashes the given password
    public static String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    // Compares a raw password with a hashed one to check if they match
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
    }
}
