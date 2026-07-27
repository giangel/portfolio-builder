package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.PortfolioThemeDAO;
import com.portfoliobuilder.dao.impl.PortfolioThemeDAOImpl;
import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.model.PortfolioTheme;
import com.portfoliobuilder.service.PortfolioThemeService;

import java.sql.SQLException;
import java.util.List;

public class PortfolioThemeServiceImpl implements PortfolioThemeService {

    private final PortfolioThemeDAO portfolioThemeDAO = new PortfolioThemeDAOImpl();

    @Override
    public List<PortfolioTheme> getActiveThemes() throws SQLException {
        return portfolioThemeDAO.findActive();
    }

    @Override
    public List<PortfolioTheme> getAllThemes() throws SQLException {
        return portfolioThemeDAO.findAll();
    }

    @Override
    public PortfolioTheme getThemeById(int themeId) throws SQLException {
        return portfolioThemeDAO.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found."));
    }

    @Override
    public void setThemeActive(int themeId, boolean active) throws SQLException {
        portfolioThemeDAO.setActive(themeId, active);
    }
}