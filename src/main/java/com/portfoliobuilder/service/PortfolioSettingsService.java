package com.portfoliobuilder.service;

import com.portfoliobuilder.model.PortfolioSettings;

import java.sql.SQLException;

public interface PortfolioSettingsService {

    PortfolioSettings getSettings(int portfolioId, int requestingUserId) throws SQLException;

    void updateSettings(int portfolioId, int requestingUserId, PortfolioSettings settings) throws SQLException;
    
    PortfolioSettings getSettingsPublic(int portfolioId) throws SQLException;
}