package com.portfoliobuilder.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?://)[\\w.-]+(:\\d+)?(/.*)?$", Pattern.CASE_INSENSITIVE);
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#[0-9A-Fa-f]{6}$");

    private ValidationUtil() {
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidUrl(String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

    public static boolean isValidHexColor(String color) {
        return color != null && HEX_COLOR_PATTERN.matcher(color).matches();
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean exceedsLength(String value, int maxLength) {
        return value != null && value.length() > maxLength;
    }
}