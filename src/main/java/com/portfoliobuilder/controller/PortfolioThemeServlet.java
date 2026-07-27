package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.PortfolioThemeService;
import com.portfoliobuilder.service.impl.PortfolioServiceImpl;
import com.portfoliobuilder.service.impl.PortfolioThemeServiceImpl;
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

@WebServlet("/portfolio/theme")
public class PortfolioThemeServlet extends HttpServlet {

    private final PortfolioService portfolioService = new PortfolioServiceImpl();
    private final PortfolioThemeService portfolioThemeService = new PortfolioThemeServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            int portfolioId = Integer.parseInt(request.getParameter("portfolioId"));
            int themeId = Integer.parseInt(request.getParameter("themeId"));

            // Confirms the theme exists and is currently active before allowing the switch,
            // an inactive theme (disabled by an administrator in Phase 13) cannot be selected.
            var selectedTheme = portfolioThemeService.getThemeById(themeId);
            if (!selectedTheme.isActive()) {
                throw new ValidationException("This theme is no longer available.");
            }

            var portfolio = portfolioService.getOwnedPortfolio(portfolioId, userId);
            portfolioService.updatePortfolio(portfolioId, userId, portfolio.getTitle(), portfolio.getDescription(), themeId);

            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=identity"));

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Portfolio or theme not found."), request, response);
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
}