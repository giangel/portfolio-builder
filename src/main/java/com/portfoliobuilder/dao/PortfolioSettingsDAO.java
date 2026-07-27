package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.PortfolioSettings;

import java.sql.SQLException;
import java.util.Optional;

public interface PortfolioSettingsDAO {

    PortfolioSettings createDefault(int portfolioId) throws SQLException;

    Optional<PortfolioSettings> findByPortfolioId(int portfolioId) throws SQLException;

    void update(PortfolioSettings settings) throws SQLException;
}