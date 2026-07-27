package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.UserProfile;
import com.portfoliobuilder.service.UserService;
import com.portfoliobuilder.service.impl.UserServiceImpl;
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

@WebServlet("/profile/update")
public class UpdateProfileServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);
        String returnPortfolioId = request.getParameter("returnPortfolioId");

        UserProfile profile = new UserProfile();
        profile.setFullName(request.getParameter("fullName"));
        profile.setHeadline(request.getParameter("headline"));
        profile.setAboutText(request.getParameter("aboutText"));
        profile.setPhone(request.getParameter("phone"));
        profile.setLocation(request.getParameter("location"));
        profile.setProfileImageUrl(request.getParameter("profileImageUrl"));

        try {
            userService.updateProfile(userId, profile);
            request.getSession().setAttribute(SessionConstants.USER_FULL_NAME, profile.getFullName());

            String target = (returnPortfolioId != null && !returnPortfolioId.isBlank())
                    ? "/portfolio/builder?portfolioId=" + returnPortfolioId + "&chapter=identity"
                    : "/profile/settings";
            response.sendRedirect(RedirectUtil.contextPath(request, target));

        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            String target = (returnPortfolioId != null && !returnPortfolioId.isBlank())
                    ? "/portfolio/builder?portfolioId=" + returnPortfolioId + "&chapter=identity"
                    : "/profile/settings";
            response.sendRedirect(RedirectUtil.contextPath(request, target));
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }
}