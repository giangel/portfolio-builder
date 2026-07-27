package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.AuditLogDAO;
import com.portfoliobuilder.dao.impl.AuditLogDAOImpl;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.AuditLog;
import com.portfoliobuilder.model.Portfolio;
import com.portfoliobuilder.model.PortfolioTheme;
import com.portfoliobuilder.model.User;
import com.portfoliobuilder.service.AdminService;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.PortfolioThemeService;
import com.portfoliobuilder.service.UserService;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminServiceImpl implements AdminService {

    private final UserService userService = new UserServiceImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();
    private final PortfolioThemeService portfolioThemeService = new PortfolioThemeServiceImpl();
    private final AuditLogDAO auditLogDAO = new AuditLogDAOImpl();

    @Override
    public Map<String, Integer> getDashboardStatistics() throws SQLException {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("totalUsers", userService.countAllUsers());
        stats.put("totalPortfolioUsers", userService.countPortfolioUsers());
        stats.put("totalPortfolios", portfolioService.countAllPortfolios());
        stats.put("publishedPortfolios", portfolioService.countPublishedPortfolios());
        stats.put("unpublishedPortfolios", portfolioService.countUnpublishedPortfolios());
        return stats;
    }

    @Override
    public List<AuditLog> getRecentActivity(int limit) throws SQLException {
        return auditLogDAO.findRecent(limit);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userService.getAllUsers();
    }

    @Override
    public void setUserActive(int adminUserId, int targetUserId, boolean active) throws SQLException {
        if (adminUserId == targetUserId && !active) {
            throw new ValidationException("You cannot deactivate your own administrator account.");
        }
        userService.setUserActive(targetUserId, active);
        auditLogDAO.log(adminUserId, active ? "ACTIVATE_USER" : "DEACTIVATE_USER", "target_user_id=" + targetUserId);
    }

    @Override
    public List<Portfolio> getAllPortfolios() throws SQLException {
        return portfolioService.getAllPortfolios();
    }

    @Override
    public List<PortfolioTheme> getAllThemes() throws SQLException {
        return portfolioThemeService.getAllThemes();
    }

    @Override
    public void setThemeActive(int adminUserId, int themeId, boolean active) throws SQLException {
        portfolioThemeService.setThemeActive(themeId, active);
        auditLogDAO.log(adminUserId, active ? "ACTIVATE_THEME" : "DEACTIVATE_THEME", "theme_id=" + themeId);
    }
}