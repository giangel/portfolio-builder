package com.portfoliobuilder.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean matches(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public static boolean isStrongEnough(String plainPassword) {
        if (plainPassword == null || plainPassword.length() < 8) {
            return false;
        }
        boolean hasLetter = plainPassword.chars().anyMatch(Character::isLetter);
        boolean hasDigit = plainPassword.chars().anyMatch(Character::isDigit);
        return hasLetter && hasDigit;
    }
}