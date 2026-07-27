package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.ProficiencyLevel;
import com.portfoliobuilder.service.SkillService;
import com.portfoliobuilder.service.impl.SkillServiceImpl;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/portfolio/skills")
public class ManageSkillsServlet extends HttpServlet {

    private final SkillService skillService = new SkillServiceImpl();

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
                case "add" -> {
                    String name = request.getParameter("skillName");
                    ProficiencyLevel level = parseLevel(request.getParameter("proficiencyLevel"));
                    skillService.addSkill(portfolioId, userId, name, level);
                }
                case "update" -> {
                    int skillId = Integer.parseInt(request.getParameter("skillId"));
                    String name = request.getParameter("skillName");
                    ProficiencyLevel level = parseLevel(request.getParameter("proficiencyLevel"));
                    skillService.updateSkill(skillId, portfolioId, userId, name, level);
                }
                case "delete" -> {
                    int skillId = Integer.parseInt(request.getParameter("skillId"));
                    skillService.deleteSkill(skillId, portfolioId, userId);
                }
                case "reorder" -> {
                    List<Integer> orderedIds = parseOrderedIds(request.getParameter("orderedIds"));
                    skillService.reorderSkills(portfolioId, userId, orderedIds);
                }
                default -> throw new ValidationException("Unknown action requested.");
            }

            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=skills"));

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Skill or portfolio not found."), request, response);
        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            String portfolioId = request.getParameter("portfolioId");
            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=skills"));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private ProficiencyLevel parseLevel(String raw) {
        try {
            return ProficiencyLevel.valueOf(raw);
        } catch (Exception e) {
            return ProficiencyLevel.INTERMEDIATE;
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