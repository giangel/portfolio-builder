package com.portfoliobuilder.util;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        String adminPassword = "admin123";
        String userPassword = "user123!";

        System.out.println("Hash for admin123:");
        System.out.println(PasswordUtil.hash(adminPassword));
        System.out.println();
        System.out.println("Hash for user123:");
        System.out.println(PasswordUtil.hash(userPassword));
    }
}