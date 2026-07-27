package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Portfolio;
import com.portfoliobuilder.model.PortfolioTheme;
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
import java.util.List;

@WebServlet("/portfolio/edit")
public class EditPortfolioServlet extends HttpServlet {

    private final PortfolioService portfolioService = new PortfolioServiceImpl();
    private final PortfolioThemeService portfolioThemeService = new PortfolioThemeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);
        int portfolioId = parsePortfolioId(request);

        try {
            Portfolio portfolio = portfolioService.getOwnedPortfolio(portfolioId, userId);
            List<PortfolioTheme> themes = portfolioThemeService.getActiveThemes();

            request.setAttribute("portfolio", portfolio);
            request.setAttribute("themes", themes);
            request.getRequestDispatcher("/views/portfolio/edit.jsp").forward(request, response);

        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);
        int portfolioId = parsePortfolioId(request);
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String themeIdParam = request.getParameter("themeId");

        try {
            int themeId = Integer.parseInt(themeIdParam);
            portfolioService.updatePortfolio(portfolioId, userId, title, description, themeId);
            response.sendRedirect(RedirectUtil.contextPath(request, "/dashboard"));

        } catch (NumberFormatException | ValidationException e) {
            String message = (e instanceof NumberFormatException) ? "Please select a valid theme." : e.getMessage();
            reshowFormWithError(request, response, portfolioId, userId, message);
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private void reshowFormWithError(HttpServletRequest request, HttpServletResponse response,
                                      int portfolioId, int userId, String message)
            throws ServletException, IOException {
        try {
            request.setAttribute("portfolio", portfolioService.getOwnedPortfolio(portfolioId, userId));
            request.setAttribute("themes", portfolioThemeService.getActiveThemes());
        } catch (SQLException ignored) {
            // If re-fetching fails here, the form will simply show without pre-filled theme options.
        }
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/views/portfolio/edit.jsp").forward(request, response);
    }

    private int parsePortfolioId(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("portfolioId"));
        } catch (NumberFormatException e) {
            throw new ResourceNotFoundException("Portfolio not found.");
        }
    }
}