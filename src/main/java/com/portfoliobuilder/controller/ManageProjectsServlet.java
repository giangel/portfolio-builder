package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Project;
import com.portfoliobuilder.service.ProjectService;
import com.portfoliobuilder.service.impl.ProjectServiceImpl;
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

@WebServlet("/portfolio/projects")
public class ManageProjectsServlet extends HttpServlet {

    private final ProjectService projectService = new ProjectServiceImpl();

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
                case "add" -> projectService.addProject(portfolioId, userId, buildFromRequest(request));
                case "update" -> {
                    int projectId = Integer.parseInt(request.getParameter("projectId"));
                    projectService.updateProject(projectId, portfolioId, userId, buildFromRequest(request));
                }
                case "delete" -> {
                    int projectId = Integer.parseInt(request.getParameter("projectId"));
                    projectService.deleteProject(projectId, portfolioId, userId);
                }
                case "reorder" -> projectService.reorderProjects(portfolioId, userId,
                        parseOrderedIds(request.getParameter("orderedIds")));
                default -> throw new ValidationException("Unknown action requested.");
            }

            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=projects"));

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Project or portfolio not found."), request, response);
        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + request.getParameter("portfolioId") + "&chapter=projects"));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private Project buildFromRequest(HttpServletRequest request) {
        Project project = new Project();
        project.setTitle(request.getParameter("title"));
        project.setDescription(request.getParameter("description"));
        project.setImageUrl(request.getParameter("imageUrl"));
        project.setProjectUrl(request.getParameter("projectUrl"));
        project.setRepositoryUrl(request.getParameter("repositoryUrl"));
        project.setStartDate(parseDate(request.getParameter("startDate")));
        project.setEndDate(parseDate(request.getParameter("endDate")));
        return project;
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