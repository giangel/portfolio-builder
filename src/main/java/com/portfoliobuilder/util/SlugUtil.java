package com.portfoliobuilder.util;

import java.util.Locale;

public class SlugUtil {

    private SlugUtil() {
    }

    public static String toBaseSlug(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "portfolio";
        }
        String normalized = input.trim().toLowerCase(Locale.ROOT);
        normalized = normalized.replaceAll("[^a-z0-9\\s-]", "");
        normalized = normalized.replaceAll("[\\s-]+", "-");
        normalized = normalized.replaceAll("^-+|-+$", "");
        return normalized.isEmpty() ? "portfolio" : normalized;
    }

    public static String appendSuffix(String baseSlug, int suffix) {
        return baseSlug + "-" + suffix;
    }
}