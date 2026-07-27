package com.portfoliobuilder.controller;

import com.portfoliobuilder.service.UserService;
import com.portfoliobuilder.service.impl.UserServiceImpl;
import com.portfoliobuilder.util.ErrorResponseUtil;
import com.portfoliobuilder.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile/settings")
public class ProfileServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);
        try {
            request.setAttribute("profile", userService.getProfile(userId));
            request.getRequestDispatcher("/views/profile/settings.jsp").forward(request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }
}