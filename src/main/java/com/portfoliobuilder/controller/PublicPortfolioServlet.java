package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.model.Portfolio;
import com.portfoliobuilder.service.*;
import com.portfoliobuilder.service.impl.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/view/*")
public class PublicPortfolioServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PublicPortfolioServlet.class.getName());

    private final PortfolioService portfolioService = new PortfolioServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final PortfolioSectionService portfolioSectionService = new PortfolioSectionServiceImpl();
    private final PortfolioSettingsService portfolioSettingsService = new PortfolioSettingsServiceImpl();
    private final SkillService skillService = new SkillServiceImpl();
    private final WorkExperienceService workExperienceService = new WorkExperienceServiceImpl();
    private final EducationService educationService = new EducationServiceImpl();
    private final ProjectService projectService = new ProjectServiceImpl();
    private final CertificationService certificationService = new CertificationServiceImpl();
    private final ServiceOfferingService serviceOfferingService = new ServiceOfferingServiceImpl();
    private final SocialLinkService socialLinkService = new SocialLinkServiceImpl();
    private final PortfolioViewService portfolioViewService = new PortfolioViewServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String slug = pathInfo.substring(1);

        try {
            Portfolio portfolio = portfolioService.getPublicPortfolioBySlug(slug);
            int portfolioId = portfolio.getPortfolioId();
            int ownerUserId = portfolio.getUserId();

            request.setAttribute("portfolio", portfolio);
            request.setAttribute("profile", userService.getProfile(ownerUserId));
            request.setAttribute("sections", portfolioSectionService.getSectionsPublic(portfolioId));
            request.setAttribute("settings", portfolioSettingsService.getSettingsPublic(portfolioId));
            request.setAttribute("skills", skillService.getSkillsPublic(portfolioId));
            request.setAttribute("experiences", workExperienceService.getExperiencesPublic(portfolioId));
            request.setAttribute("educations", educationService.getEducationsPublic(portfolioId));
            request.setAttribute("projects", projectService.getProjectsPublic(portfolioId));
            request.setAttribute("certifications", certificationService.getCertificationsPublic(portfolioId));
            request.setAttribute("services", serviceOfferingService.getServiceOfferingsPublic(portfolioId));
            request.setAttribute("socialLinks", socialLinkService.getSocialLinksPublic(portfolioId));
            request.setAttribute("isPreview", false);

            recordViewSafely(request, portfolioId);

            request.getRequestDispatcher("/views/public/portfolio.jsp").forward(request, response);

        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error rendering public portfolio for slug " + slug, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void recordViewSafely(HttpServletRequest request, int portfolioId) {
        try {
            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            portfolioViewService.recordView(portfolioId, ip, userAgent);
        } catch (SQLException e) {
            // View tracking is best effort analytics, a failure here must never prevent
            // a visitor from seeing the portfolio itself, so it is logged and swallowed.
            LOGGER.log(Level.WARNING, "Failed to record portfolio view for portfolio_id " + portfolioId, e);
        }
    }
}