package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.SocialLink;
import com.portfoliobuilder.model.SocialPlatform;
import com.portfoliobuilder.service.SocialLinkService;
import com.portfoliobuilder.service.impl.SocialLinkServiceImpl;
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

@WebServlet("/portfolio/social-links")
public class ManageSocialLinksServlet extends HttpServlet {

    private final SocialLinkService socialLinkService = new SocialLinkServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        
        try {
            int portfolioId = Integer.parseInt(request.getParameter("portfolioId"));

            switch (action) {
                case "add" -> socialLinkService.addSocialLink(portfolioId, userId, buildFromRequest(request));
                case "update" -> {
                    int socialLinkId = Integer.parseInt(request.getParameter("socialLinkId"));
                    socialLinkService.updateSocialLink(socialLinkId, portfolioId, userId, buildFromRequest(request));
                }
                case "delete" -> {
                    int socialLinkId = Integer.parseInt(request.getParameter("socialLinkId"));
                    socialLinkService.deleteSocialLink(socialLinkId, portfolioId, userId);
                }
                default -> throw new ValidationException("Unknown action requested.");
            }

            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=social"));

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Social link or portfolio not found."), request, response);
        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + request.getParameter("portfolioId") + "&chapter=social"));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private SocialLink buildFromRequest(HttpServletRequest request) {
        SocialLink socialLink = new SocialLink();
        try {
            socialLink.setPlatform(SocialPlatform.valueOf(request.getParameter("platform")));
        } catch (Exception e) {
            throw new ValidationException("Please select a valid platform.");
        }
        socialLink.setUrl(request.getParameter("url"));
        return socialLink;
    }
}