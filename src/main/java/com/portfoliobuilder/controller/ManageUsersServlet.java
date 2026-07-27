package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.service.AdminService;
import com.portfoliobuilder.service.impl.AdminServiceImpl;
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

@WebServlet("/admin/users")
public class ManageUsersServlet extends HttpServlet {

    private final AdminService adminService = new AdminServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("users", adminService.getAllUsers());
            request.getRequestDispatcher("/views/admin/users.jsp").forward(request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int adminUserId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            int targetUserId = Integer.parseInt(request.getParameter("userId"));
            boolean active = "true".equals(request.getParameter("active"));
            adminService.setUserActive(adminUserId, targetUserId, active);
            response.sendRedirect(RedirectUtil.contextPath(request, "/admin/users"));

        } catch (NumberFormatException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, "User not found.");
            response.sendRedirect(RedirectUtil.contextPath(request, "/admin/users"));
        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            response.sendRedirect(RedirectUtil.contextPath(request, "/admin/users"));
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }
}