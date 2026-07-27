package com.portfoliobuilder.service;

import com.portfoliobuilder.model.Portfolio;

import java.sql.SQLException;
import java.util.List;

public interface PortfolioService {

    Portfolio createPortfolio(int userId, String title, String description, int themeId) throws SQLException;

    Portfolio getOwnedPortfolio(int portfolioId, int requestingUserId) throws SQLException;

    Portfolio getPublicPortfolioBySlug(String slug) throws SQLException;

    List<Portfolio> getPortfoliosForUser(int userId) throws SQLException;

    void updatePortfolio(int portfolioId, int requestingUserId, String title, String description, int themeId) throws SQLException;

    void deletePortfolio(int portfolioId, int requestingUserId) throws SQLException;

    void publish(int portfolioId, int requestingUserId) throws SQLException;

    void unpublish(int portfolioId, int requestingUserId) throws SQLException;

    List<Portfolio> getAllPortfolios() throws SQLException;

    int countAllPortfolios() throws SQLException;

    int countPublishedPortfolios() throws SQLException;

    int countUnpublishedPortfolios() throws SQLException;
}