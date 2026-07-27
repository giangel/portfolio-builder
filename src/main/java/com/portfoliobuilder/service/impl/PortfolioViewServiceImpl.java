package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.PortfolioViewDAO;
import com.portfoliobuilder.dao.impl.PortfolioViewDAOImpl;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.PortfolioViewService;

import java.sql.SQLException;

public class PortfolioViewServiceImpl implements PortfolioViewService {

    private static final int MAX_IP_LENGTH = 45;
    private static final int MAX_USER_AGENT_LENGTH = 300;

    private final PortfolioViewDAO portfolioViewDAO = new PortfolioViewDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public void recordView(int portfolioId, String ipAddress, String userAgent) throws SQLException {
        // Intentionally does not call getOwnedPortfolio, view recording happens on the public,
        // unauthenticated route and must work for anonymous visitors. The portfolio's published
        // state is already confirmed by PublicPortfolioServlet before this is called.
        portfolioViewDAO.recordView(portfolioId, truncate(ipAddress, MAX_IP_LENGTH), truncate(userAgent, MAX_USER_AGENT_LENGTH));
    }

    @Override
    public int getViewCount(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return portfolioViewDAO.countViewsForPortfolio(portfolioId);
    }

    private String truncate(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        return value.length() > maxLength ? value.substring(0, maxLength) : value;
    }
}