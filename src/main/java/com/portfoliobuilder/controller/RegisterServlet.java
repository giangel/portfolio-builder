package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.User;
import com.portfoliobuilder.service.AuthenticationService;
import com.portfoliobuilder.service.impl.AuthenticationServiceImpl;
import com.portfoliobuilder.util.RedirectUtil;
import com.portfoliobuilder.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());
    private final AuthenticationService authenticationService = new AuthenticationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            if (password == null || !password.equals(confirmPassword)) {
                throw new ValidationException("Password and confirmation password do not match.");
            }

            User user = authenticationService.register(email, password, fullName);

            HttpSession session = request.getSession(true);
            session.setAttribute(SessionConstants.USER_ID, user.getUserId());
            session.setAttribute(SessionConstants.USER_EMAIL, user.getEmail());
            session.setAttribute(SessionConstants.USER_FULL_NAME, fullName.trim());
            session.setAttribute(SessionConstants.USER_ROLE, user.getRoleName().name());

            response.sendRedirect(RedirectUtil.contextPath(request, "/dashboard"));

        } catch (ValidationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("submittedFullName", fullName);
            request.setAttribute("submittedEmail", email);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during registration", e);
            request.setAttribute("errorMessage", "Something went wrong while creating your account. Please try again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}