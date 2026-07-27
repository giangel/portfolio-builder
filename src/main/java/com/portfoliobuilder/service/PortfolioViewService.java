package com.portfoliobuilder.service;

import java.sql.SQLException;

public interface PortfolioViewService {

    void recordView(int portfolioId, String ipAddress, String userAgent) throws SQLException;

    int getViewCount(int portfolioId, int requestingUserId) throws SQLException;
}