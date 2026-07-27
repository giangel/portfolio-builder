package com.portfoliobuilder.controller;

import com.portfoliobuilder.model.Portfolio;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.impl.PortfolioServiceImpl;
import com.portfoliobuilder.util.ErrorResponseUtil;
import com.portfoliobuilder.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/dashboard")
public class PortfolioDashboardServlet extends HttpServlet {

    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            List<Portfolio> portfolios = portfolioService.getPortfoliosForUser(userId);

            long publishedCount = portfolios.stream().filter(Portfolio::isPublished).count();
            long draftCount = portfolios.size() - publishedCount;

            request.setAttribute("portfolios", portfolios);
            request.setAttribute("publishedCount", publishedCount);
            request.setAttribute("draftCount", draftCount);
            request.getRequestDispatcher("/views/dashboard/dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }
}