package com.portfoliobuilder.service;

import com.portfoliobuilder.model.AuditLog;
import com.portfoliobuilder.model.Portfolio;
import com.portfoliobuilder.model.PortfolioTheme;
import com.portfoliobuilder.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AdminService {

    Map<String, Integer> getDashboardStatistics() throws SQLException;

    List<AuditLog> getRecentActivity(int limit) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    void setUserActive(int adminUserId, int targetUserId, boolean active) throws SQLException;

    List<Portfolio> getAllPortfolios() throws SQLException;

    List<PortfolioTheme> getAllThemes() throws SQLException;

    void setThemeActive(int adminUserId, int themeId, boolean active) throws SQLException;
}