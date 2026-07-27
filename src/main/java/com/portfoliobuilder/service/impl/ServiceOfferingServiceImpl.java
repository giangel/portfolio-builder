package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.ServiceDAO;
import com.portfoliobuilder.dao.impl.ServiceDAOImpl;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Service;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.ServiceOfferingService;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceDAO serviceDAO = new ServiceDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public Service addServiceOffering(int portfolioId, int requestingUserId, Service serviceOffering) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(serviceOffering);
        serviceOffering.setPortfolioId(portfolioId);
        serviceOffering.setDisplayOrder(serviceDAO.findByPortfolioId(portfolioId).size() + 1);
        return serviceDAO.create(serviceOffering);
    }

    @Override
    public List<Service> getServiceOfferings(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return serviceDAO.findByPortfolioId(portfolioId);
    }

    @Override
    public void updateServiceOffering(int serviceId, int portfolioId, int requestingUserId, Service serviceOffering) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(serviceOffering);
        serviceOffering.setServiceId(serviceId);
        serviceDAO.update(serviceOffering, portfolioId);
    }

    @Override
    public void deleteServiceOffering(int serviceId, int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        serviceDAO.delete(serviceId, portfolioId);
    }

    @Override
    public void reorderServiceOfferings(int portfolioId, int requestingUserId, List<Integer> orderedServiceIds) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        serviceDAO.reorder(portfolioId, orderedServiceIds);
    }

    private void validate(Service serviceOffering) {
        if (ValidationUtil.isBlank(serviceOffering.getTitle())) {
            throw new ValidationException("Service title is required.");
        }
    }
    
    @Override
    public List<Service> getServiceOfferingsPublic(int portfolioId) throws SQLException {
        return serviceDAO.findByPortfolioId(portfolioId);
    }
}