package com.portfoliobuilder.controller;

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

@WebServlet("/admin/themes")
public class ManageThemesServlet extends HttpServlet {

    private final AdminService adminService = new AdminServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("themes", adminService.getAllThemes());
            request.getRequestDispatcher("/views/admin/themes.jsp").forward(request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int adminUserId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            int themeId = Integer.parseInt(request.getParameter("themeId"));
            boolean active = "true".equals(request.getParameter("active"));
            adminService.setThemeActive(adminUserId, themeId, active);
            response.sendRedirect(RedirectUtil.contextPath(request, "/admin/themes"));

        } catch (NumberFormatException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, "Theme not found.");
            response.sendRedirect(RedirectUtil.contextPath(request, "/admin/themes"));
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }
}