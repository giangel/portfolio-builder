package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.PortfolioViewRecord;

import java.sql.SQLException;
import java.util.List;

public interface PortfolioViewDAO {

    void recordView(int portfolioId, String ipAddress, String userAgent) throws SQLException;

    int countViewsForPortfolio(int portfolioId) throws SQLException;

    List<PortfolioViewRecord> findRecentForPortfolio(int portfolioId, int limit) throws SQLException;
}