package com.portfoliobuilder.controller;

import com.portfoliobuilder.service.AdminService;
import com.portfoliobuilder.service.impl.AdminServiceImpl;
import com.portfoliobuilder.util.ErrorResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final AdminService adminService = new AdminServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("statistics", adminService.getDashboardStatistics());
            request.setAttribute("recentActivity", adminService.getRecentActivity(15));
            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }
}