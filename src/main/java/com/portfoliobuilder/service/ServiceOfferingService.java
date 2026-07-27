package com.portfoliobuilder.service;

import com.portfoliobuilder.model.Service;

import java.sql.SQLException;
import java.util.List;

public interface ServiceOfferingService {

    Service addServiceOffering(int portfolioId, int requestingUserId, Service serviceOffering) throws SQLException;

    List<Service> getServiceOfferings(int portfolioId, int requestingUserId) throws SQLException;

    void updateServiceOffering(int serviceId, int portfolioId, int requestingUserId, Service serviceOffering) throws SQLException;

    void deleteServiceOffering(int serviceId, int portfolioId, int requestingUserId) throws SQLException;

    void reorderServiceOfferings(int portfolioId, int requestingUserId, List<Integer> orderedServiceIds) throws SQLException;

    List<Service> getServiceOfferingsPublic(int portfolioId) throws SQLException;
    
}