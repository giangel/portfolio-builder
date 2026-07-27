package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Education;
import com.portfoliobuilder.service.EducationService;
import com.portfoliobuilder.service.impl.EducationServiceImpl;
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

@WebServlet("/portfolio/education")
public class ManageEducationServlet extends HttpServlet {

    private final EducationService educationService = new EducationServiceImpl();

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
                case "add" -> educationService.addEducation(portfolioId, userId, buildFromRequest(request));
                case "update" -> {
                    int educationId = Integer.parseInt(request.getParameter("educationId"));
                    educationService.updateEducation(educationId, portfolioId, userId, buildFromRequest(request));
                }
                case "delete" -> {
                    int educationId = Integer.parseInt(request.getParameter("educationId"));
                    educationService.deleteEducation(educationId, portfolioId, userId);
                }
                case "reorder" -> educationService.reorderEducations(portfolioId, userId,
                        parseOrderedIds(request.getParameter("orderedIds")));
                default -> throw new ValidationException("Unknown action requested.");
            }

            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=education"));

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Education entry or portfolio not found."), request, response);
        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + request.getParameter("portfolioId") + "&chapter=education"));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private Education buildFromRequest(HttpServletRequest request) {
        Education education = new Education();
        education.setInstitutionName(request.getParameter("institutionName"));
        education.setDegree(request.getParameter("degree"));
        education.setFieldOfStudy(request.getParameter("fieldOfStudy"));
        education.setStartDate(parseDate(request.getParameter("startDate")));
        education.setEndDate(parseDate(request.getParameter("endDate")));
        education.setDescription(request.getParameter("description"));
        return education;
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