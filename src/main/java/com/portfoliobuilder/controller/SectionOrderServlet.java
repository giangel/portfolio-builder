package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.service.PortfolioSectionService;
import com.portfoliobuilder.service.impl.PortfolioSectionServiceImpl;
import com.portfoliobuilder.util.ErrorResponseUtil;
import com.portfoliobuilder.util.RedirectUtil;
import com.portfoliobuilder.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/portfolio/section-order")
public class SectionOrderServlet extends HttpServlet {

    private final PortfolioSectionService portfolioSectionService = new PortfolioSectionServiceImpl();

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
                case "toggle" -> {
                    int sectionId = Integer.parseInt(request.getParameter("sectionId"));
                    boolean enabled = "true".equals(request.getParameter("enabled"));
                    portfolioSectionService.toggleSection(sectionId, portfolioId, userId, enabled);
                }
                case "reorder" -> {
                    List<Integer> orderedIds = parseOrderedIds(request.getParameter("orderedIds"));
                    if (orderedIds.isEmpty()) {
                        throw new ValidationException("No section order was submitted.");
                    }
                    portfolioSectionService.reorderSections(portfolioId, userId, orderedIds);
                }
                default -> throw new ValidationException("Unknown action requested.");
            }

            // Reordering is triggered by drag-and-drop JavaScript (built in Phase 14) using a
            // background request, so this responds with a small JSON body instead of a redirect,
            // letting the script update the rail without a full page reload. Toggling a section's
            // visibility uses a normal form post and still redirects back into the builder.
            if ("reorder".equals(action)) {
                writeJsonSuccess(response);
            } else {
                response.sendRedirect(RedirectUtil.contextPath(request,
                        "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=publish"));
            }

        } catch (NumberFormatException e) {
            respondWithError(request, response, action, new ResourceNotFoundException("Section or portfolio not found."));
        } catch (ValidationException e) {
            respondWithError(request, response, action, e);
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            respondWithError(request, response, action, e);
        } catch (SQLException e) {
            respondWithError(request, response, action, e);
        }
    }

    private void respondWithError(HttpServletRequest request, HttpServletResponse response, String action, Exception e)
            throws IOException {
        if ("reorder".equals(action)) {
            writeJsonError(response, e.getMessage() != null ? e.getMessage() : "Unable to save section order.");
        } else {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR,
                    e.getMessage() != null ? e.getMessage() : "Unable to update section.");
            try {
                response.sendRedirect(RedirectUtil.contextPath(request,
                        "/portfolio/builder?portfolioId=" + request.getParameter("portfolioId") + "&chapter=publish"));
            } catch (IOException ignored) {
                // Response already committed, nothing further to do.
            }
        }
    }

    private void writeJsonSuccess(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("{\"success\":true}");
        }
    }

    private void writeJsonError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("{\"success\":false,\"message\":\"" + message.replace("\"", "'") + "\"}");
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