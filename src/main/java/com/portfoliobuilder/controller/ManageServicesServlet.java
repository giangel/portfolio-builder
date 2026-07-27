package com.portfoliobuilder.controller;

import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Service;
import com.portfoliobuilder.service.ServiceOfferingService;
import com.portfoliobuilder.service.impl.ServiceOfferingServiceImpl;
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

@WebServlet("/portfolio/services")
public class ManageServicesServlet extends HttpServlet {

    private final ServiceOfferingService serviceOfferingService = new ServiceOfferingServiceImpl();

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
                case "add" -> serviceOfferingService.addServiceOffering(portfolioId, userId, buildFromRequest(request));
                case "update" -> {
                    int serviceId = Integer.parseInt(request.getParameter("serviceId"));
                    serviceOfferingService.updateServiceOffering(serviceId, portfolioId, userId, buildFromRequest(request));
                }
                case "delete" -> {
                    int serviceId = Integer.parseInt(request.getParameter("serviceId"));
                    serviceOfferingService.deleteServiceOffering(serviceId, portfolioId, userId);
                }
                case "reorder" -> serviceOfferingService.reorderServiceOfferings(portfolioId, userId,
                        parseOrderedIds(request.getParameter("orderedIds")));
                default -> throw new ValidationException("Unknown action requested.");
            }

            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + portfolioId + "&chapter=services"));

        } catch (NumberFormatException e) {
            ErrorResponseUtil.handle(new ResourceNotFoundException("Service or portfolio not found."), request, response);
        } catch (ValidationException e) {
            request.getSession().setAttribute(SessionConstants.FLASH_ERROR, e.getMessage());
            response.sendRedirect(RedirectUtil.contextPath(request,
                    "/portfolio/builder?portfolioId=" + request.getParameter("portfolioId") + "&chapter=services"));
        } catch (ResourceNotFoundException | UnauthorizedActionException e) {
            ErrorResponseUtil.handle(e, request, response);
        } catch (SQLException e) {
            ErrorResponseUtil.handle(e, request, response);
        }
    }

    private Service buildFromRequest(HttpServletRequest request) {
        Service service = new Service();
        service.setTitle(request.getParameter("title"));
        service.setDescription(request.getParameter("description"));
        service.setIconKey(request.getParameter("iconKey"));
        return service;
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