package com.portfoliobuilder.controller;

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

@WebServlet("/portfolio/create")
public class CreatePortfolioServlet extends HttpServlet {

    private final PortfolioService portfolioService = new PortfolioServiceImpl();
    private final PortfolioThemeService portfolioThemeService = new PortfolioThemeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<PortfolioTheme> themes = portfolioThemeService.getActiveThemes();
            request.setAttribute("themes", themes);
            request.getRequestDispatcher("/views/portfolio/create.jsp").forward(request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String themeIdParam = request.getParameter("themeId");

        try {
            int themeId = Integer.parseInt(themeIdParam);
            Portfolio created = portfolioService.createPortfolio(userId, title, description, themeId);
            response.sendRedirect(RedirectUtil.contextPath(request, "/portfolio/builder?portfolioId=" + created.getPortfolioId()));

        } catch (NumberFormatException e) {
            reshowFormWithError(request, response, "Please select a valid theme.");
        } catch (ValidationException e) {
            reshowFormWithError(request, response, e.getMessage());
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private void reshowFormWithError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        try {
            request.setAttribute("themes", portfolioThemeService.getActiveThemes());
        } catch (SQLException ignored) {
            request.setAttribute("themes", List.of());
        }
        request.setAttribute("errorMessage", message);
        request.setAttribute("submittedTitle", request.getParameter("title"));
        request.setAttribute("submittedDescription", request.getParameter("description"));
        request.getRequestDispatcher("/views/portfolio/create.jsp").forward(request, response);
    }
}