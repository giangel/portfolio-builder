package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.AuthenticationException;
import com.portfoliobuilder.model.User;
import com.portfoliobuilder.model.UserProfile;
import com.portfoliobuilder.service.AuthenticationService;
import com.portfoliobuilder.service.UserService;
import com.portfoliobuilder.service.impl.AuthenticationServiceImpl;
import com.portfoliobuilder.service.impl.UserServiceImpl;
import com.portfoliobuilder.util.RedirectUtil;
import com.portfoliobuilder.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private final AuthenticationService authenticationService = new AuthenticationServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String redirectTo = request.getParameter("redirectTo");

        try {
            User user = authenticationService.login(email, password);

            HttpSession session = request.getSession(true);
            session.setAttribute(SessionConstants.USER_ID, user.getUserId());
            session.setAttribute(SessionConstants.USER_EMAIL, user.getEmail());
            session.setAttribute(SessionConstants.USER_ROLE, user.getRoleName().name());

            Optional<UserProfile> profile = java.util.Optional.empty();
            try {
                profile = java.util.Optional.of(userService.getProfile(user.getUserId()));
            } catch (RuntimeException ignored) {
                // Profile is expected to always exist for a registered user, this guard
                // simply prevents a missing profile from blocking a successful login.
            }
            session.setAttribute(SessionConstants.USER_FULL_NAME,
                    profile.map(UserProfile::getFullName).orElse(user.getEmail()));

            String target = resolveRedirectTarget(request, redirectTo, user.getRoleName().name());
            response.sendRedirect(target);

        } catch (AuthenticationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("submittedEmail", email);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error during login", e);
            request.setAttribute("errorMessage", "Something went wrong while signing you in. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private String resolveRedirectTarget(HttpServletRequest request, String redirectTo, String roleName) {
        if (redirectTo != null && !redirectTo.isBlank()) {
            String decoded = URLDecoder.decode(redirectTo, StandardCharsets.UTF_8);
            if (decoded.startsWith(request.getContextPath() + "/")) {
                return decoded;
            }
        }
        return "ADMINISTRATOR".equals(roleName)
                ? RedirectUtil.contextPath(request, "/admin/dashboard")
                : RedirectUtil.contextPath(request, "/dashboard");
    }
}