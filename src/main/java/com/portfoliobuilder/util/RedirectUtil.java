package com.portfoliobuilder.util;

import jakarta.servlet.http.HttpServletRequest;

public class RedirectUtil {

    private RedirectUtil() {
    }

    public static String contextPath(HttpServletRequest request, String path) {
        String context = request.getContextPath();
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return context + path;
    }
}