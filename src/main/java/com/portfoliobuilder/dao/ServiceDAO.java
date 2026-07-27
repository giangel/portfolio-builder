package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ServiceDAO {

    Service create(Service service) throws SQLException;

    Optional<Service> findById(int serviceId) throws SQLException;

    List<Service> findByPortfolioId(int portfolioId) throws SQLException;

    void update(Service service, int portfolioId) throws SQLException;

    void delete(int serviceId, int portfolioId) throws SQLException;

    void reorder(int portfolioId, List<Integer> orderedServiceIds) throws SQLException;
}