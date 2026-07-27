package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.model.Portfolio;
import com.portfoliobuilder.model.UserProfile;
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
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/portfolio/builder")
public class PortfolioBuilderServlet extends HttpServlet {

    private final PortfolioService portfolioService = new PortfolioServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final PortfolioSectionService portfolioSectionService = new PortfolioSectionServiceImpl();
    private final PortfolioSettingsService portfolioSettingsService = new PortfolioSettingsServiceImpl();
    private final PortfolioThemeService portfolioThemeService = new PortfolioThemeServiceImpl();
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
        String chapter = request.getParameter("chapter");
        if (chapter == null || chapter.isBlank()) {
            chapter = "identity";
        }

        try {
            int portfolioId = Integer.parseInt(request.getParameter("portfolioId"));
            Portfolio portfolio = portfolioService.getOwnedPortfolio(portfolioId, userId);

            Map<String, Integer> chapterCounts = new LinkedHashMap<>();
            chapterCounts.put("skills", skillService.getSkills(portfolioId, userId).size());
            chapterCounts.put("experience", workExperienceService.getExperiences(portfolioId, userId).size());
            chapterCounts.put("education", educationService.getEducations(portfolioId, userId).size());
            chapterCounts.put("projects", projectService.getProjects(portfolioId, userId).size());
            chapterCounts.put("certifications", certificationService.getCertifications(portfolioId, userId).size());
            chapterCounts.put("services", serviceOfferingService.getServiceOfferings(portfolioId, userId).size());
            chapterCounts.put("social", socialLinkService.getSocialLinks(portfolioId, userId).size());

            request.setAttribute("portfolio", portfolio);
            request.setAttribute("currentChapter", chapter);
            request.setAttribute("chapterCounts", chapterCounts);

            switch (chapter) {
                case "identity" -> {
                    request.setAttribute("profile", userService.getProfile(userId));
                    request.setAttribute("settings", portfolioSettingsService.getSettings(portfolioId, userId));
                    request.setAttribute("themes", portfolioThemeService.getActiveThemes());
                }
                case "skills" -> request.setAttribute("skills", skillService.getSkills(portfolioId, userId));
                case "experience" -> request.setAttribute("experiences", workExperienceService.getExperiences(portfolioId, userId));
                case "education" -> request.setAttribute("educations", educationService.getEducations(portfolioId, userId));
                case "projects" -> request.setAttribute("projects", projectService.getProjects(portfolioId, userId));
                case "certifications" -> request.setAttribute("certifications", certificationService.getCertifications(portfolioId, userId));
                case "services" -> request.setAttribute("services", serviceOfferingService.getServiceOfferings(portfolioId, userId));
                case "social" -> request.setAttribute("socialLinks", socialLinkService.getSocialLinks(portfolioId, userId));
                case "publish" -> request.setAttribute("sections", portfolioSectionService.getSections(portfolioId, userId));
                default -> request.setAttribute("currentChapter", "identity");
            }

            request.getRequestDispatcher("/views/portfolio/builder.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Portfolio not found."), request, response);
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }
}