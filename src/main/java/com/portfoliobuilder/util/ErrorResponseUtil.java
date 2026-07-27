package com.portfoliobuilder.util;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorResponseUtil {

    private static final Logger LOGGER = Logger.getLogger(ErrorResponseUtil.class.getName());

    private ErrorResponseUtil() {
    }

    public static void handle(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (e instanceof ResourceNotFoundException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
        if (e instanceof UnauthorizedActionException) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
        // ValidationException and AuthenticationException are handled inline by the
        // Servlets that throw them, since those need to redisplay a form with the
        // message rather than navigate away. Anything else is unexpected: log it
        // server side and show the generic 500 page, never the raw exception.
        LOGGER.log(Level.SEVERE, "Unhandled exception on " + request.getRequestURI(), e);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}