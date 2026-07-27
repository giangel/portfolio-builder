package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.PortfolioTheme;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PortfolioThemeDAO {

    List<PortfolioTheme> findAll() throws SQLException;

    List<PortfolioTheme> findActive() throws SQLException;

    Optional<PortfolioTheme> findById(int themeId) throws SQLException;

    Optional<PortfolioTheme> findByKey(String themeKey) throws SQLException;

    void setActive(int themeId, boolean active) throws SQLException;
}