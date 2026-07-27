package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Certification;
import com.portfoliobuilder.service.CertificationService;
import com.portfoliobuilder.service.impl.CertificationServiceImpl;
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

@WebServlet("/portfolio/certifications")
public class ManageCertificationsServlet extends HttpServlet {

    private final CertificationService certificationService = new CertificationServiceImpl();

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
                case "add" -> certificationService.addCertification(portfolioId, userId, buildFromRequest(request));
                case "update" -> {
                    int certificationId = Integer.parseInt(request.getParameter("certificationId"));
                    certificationService.updateCertification(certificationId, portfolioId, userId, buildFromRequest(request));
                }
                case "delete" -> {
                    int certificationId = Integer.parseInt(request.getParameter("certificationId"));
                    certificationService.deleteCertification(certificationId, portfolioId, userId);
                }
                case "reorder" -> certificationService.reorderCertifications(portfolioId, userId,
                        parseOrderedIds(request.getParameter("orderedIds")));
                default -> throw new ValidationException("Unknown action requested.");
            }

            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=certifications"));

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Certification or portfolio not found."), request, response);
        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + request.getParameter("portfolioId") + "&chapter=certifications"));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private Certification buildFromRequest(HttpServletRequest request) {
        Certification certification = new Certification();
        certification.setCertificationName(request.getParameter("certificationName"));
        certification.setIssuingOrganization(request.getParameter("issuingOrganization"));
        certification.setIssueDate(parseDate(request.getParameter("issueDate")));
        certification.setExpirationDate(parseDate(request.getParameter("expirationDate")));
        certification.setCredentialUrl(request.getParameter("credentialUrl"));
        return certification;
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