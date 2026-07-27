package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.model.Portfolio;
import com.portfoliobuilder.service.*;
import com.portfoliobuilder.service.impl.*;
import com.portfoliobuilder.util.ErrorResponseUtil;
import com.portfoliobuilder.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/portfolio/preview")
public class PreviewPortfolioServlet extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            int portfolioId = Integer.parseInt(request.getParameter("portfolioId"));
            Portfolio portfolio = portfolioService.getOwnedPortfolio(portfolioId, userId);

            request.setAttribute("portfolio", portfolio);
            request.setAttribute("profile", userService.getProfile(userId));
            request.setAttribute("sections", portfolioSectionService.getSections(portfolioId, userId));
            request.setAttribute("settings", portfolioSettingsService.getSettings(portfolioId, userId));
            request.setAttribute("skills", skillService.getSkills(portfolioId, userId));
            request.setAttribute("experiences", workExperienceService.getExperiences(portfolioId, userId));
            request.setAttribute("educations", educationService.getEducations(portfolioId, userId));
            request.setAttribute("projects", projectService.getProjects(portfolioId, userId));
            request.setAttribute("certifications", certificationService.getCertifications(portfolioId, userId));
            request.setAttribute("services", serviceOfferingService.getServiceOfferings(portfolioId, userId));
            request.setAttribute("socialLinks", socialLinkService.getSocialLinks(portfolioId, userId));
            request.setAttribute("isPreview", true);

            request.getRequestDispatcher("/views/portfolio/preview.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Portfolio not found."), request, response);
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }
}