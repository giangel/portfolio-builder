package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.PortfolioSettingsDAO;
import com.portfoliobuilder.dao.impl.PortfolioSettingsDAOImpl;
import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.PortfolioSettings;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.PortfolioSettingsService;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;

public class PortfolioSettingsServiceImpl implements PortfolioSettingsService {

    private final PortfolioSettingsDAO portfolioSettingsDAO = new PortfolioSettingsDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public PortfolioSettings getSettings(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return portfolioSettingsDAO.findByPortfolioId(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio settings not found."));
    }

    @Override
    public void updateSettings(int portfolioId, int requestingUserId, PortfolioSettings settings) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        if (!ValidationUtil.isValidHexColor(settings.getAccentColor())) {
            throw new ValidationException("Accent color must be a valid hex color, for example #C97B5C.");
        }
        settings.setPortfolioId(portfolioId);
        portfolioSettingsDAO.update(settings);
    }
    
    @Override
    public PortfolioSettings getSettingsPublic(int portfolioId) throws SQLException {
        return portfolioSettingsDAO.findByPortfolioId(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio settings not found."));
    }
}