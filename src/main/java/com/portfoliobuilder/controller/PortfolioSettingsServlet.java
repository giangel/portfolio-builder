package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.PortfolioSettings;
import com.portfoliobuilder.service.PortfolioSettingsService;
import com.portfoliobuilder.service.impl.PortfolioSettingsServiceImpl;
import com.portfoliobuilder.util.ErrorResponseUtil;
import com.portfoliobuilder.util.RedirectUtil;
import com.portfoliobuilder.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/portfolio/settings")
public class PortfolioSettingsServlet extends HttpServlet {

    private static final Set<String> VALID_TYPOGRAPHY = Set.of(
            "storyline-serif", "modern-sans", "editorial-mix", "developer-mono");
    private static final Set<String> VALID_BACKGROUND = Set.of(
            "paper", "clean-white", "soft-gray", "dark");
    private static final Set<String> VALID_BUTTON = Set.of("rounded", "square", "pill");
    private static final Set<String> VALID_LAYOUT = Set.of("standard", "compact", "spacious");

    private final PortfolioSettingsService portfolioSettingsService = new PortfolioSettingsServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            int portfolioId = Integer.parseInt(request.getParameter("portfolioId"));

            PortfolioSettings settings = new PortfolioSettings();
            settings.setAccentColor(request.getParameter("accentColor"));
            settings.setTypographyChoice(validatedChoice(request.getParameter("typographyChoice"), VALID_TYPOGRAPHY, "typography"));
            settings.setBackgroundStyle(validatedChoice(request.getParameter("backgroundStyle"), VALID_BACKGROUND, "background style"));
            settings.setButtonStyle(validatedChoice(request.getParameter("buttonStyle"), VALID_BUTTON, "button style"));
            settings.setLayoutVariant(validatedChoice(request.getParameter("layoutVariant"), VALID_LAYOUT, "layout"));

            portfolioSettingsService.updateSettings(portfolioId, userId, settings);

            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=identity"));

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Portfolio not found."), request, response);
        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + request.getParameter("portfolioId") + "&chapter=identity"));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private String validatedChoice(String value, Set<String> allowed, String fieldLabel) {
        if (value == null || !allowed.contains(value)) {
            throw new ValidationException("Please select a valid " + fieldLabel + " option.");
        }
        return value;
    }
}