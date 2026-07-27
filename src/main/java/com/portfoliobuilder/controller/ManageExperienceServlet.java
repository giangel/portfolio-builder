package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.WorkExperience;
import com.portfoliobuilder.service.WorkExperienceService;
import com.portfoliobuilder.service.impl.WorkExperienceServiceImpl;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/portfolio/experience")
public class ManageExperienceServlet extends HttpServlet {

    private final WorkExperienceService workExperienceService = new WorkExperienceServiceImpl();

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
                case "add" -> workExperienceService.addExperience(portfolioId, userId, buildFromRequest(request));
                case "update" -> {
                    int experienceId = Integer.parseInt(request.getParameter("experienceId"));
                    workExperienceService.updateExperience(experienceId, portfolioId, userId, buildFromRequest(request));
                }
                case "delete" -> {
                    int experienceId = Integer.parseInt(request.getParameter("experienceId"));
                    workExperienceService.deleteExperience(experienceId, portfolioId, userId);
                }
                case "reorder" -> workExperienceService.reorderExperiences(portfolioId, userId,
                        parseOrderedIds(request.getParameter("orderedIds")));
                default -> throw new ValidationException("Unknown action requested.");
            }

            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=experience"));

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Experience entry or portfolio not found."), request, response);
        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + request.getParameter("portfolioId") + "&chapter=experience"));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private WorkExperience buildFromRequest(HttpServletRequest request) {
        WorkExperience experience = new WorkExperience();
        experience.setJobTitle(request.getParameter("jobTitle"));
        experience.setCompanyName(request.getParameter("companyName"));
        experience.setLocation(request.getParameter("location"));
        experience.setStartDate(parseDate(request.getParameter("startDate")));
        experience.setCurrent("on".equals(request.getParameter("isCurrent")));
        experience.setEndDate(experience.isCurrent() ? null : parseDate(request.getParameter("endDate")));
        experience.setDescription(request.getParameter("description"));
        return experience;
    }

    private LocalDate parseDate(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(raw);
        } catch (Exception e) {
            throw new ValidationException("Please enter valid dates in YYYY-MM-DD format.");
        }
    }

    private List<Integer> parseOrderedIds(String csv) {
        List<Integer> ids = new ArrayList<>();
        if (csv != null && !csv.isBlank()) {
            for (String part : csv.split(",")) {
                ids.add(Integer.parseInt(part.trim()));
            }
        }
        return ids;
    }
}