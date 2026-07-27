package com.portfoliobuilder.service;

import com.portfoliobuilder.model.PortfolioTheme;

import java.sql.SQLException;
import java.util.List;

public interface PortfolioThemeService {

    List<PortfolioTheme> getActiveThemes() throws SQLException;

    List<PortfolioTheme> getAllThemes() throws SQLException;

    PortfolioTheme getThemeById(int themeId) throws SQLException;

    void setThemeActive(int themeId, boolean active) throws SQLException;
}