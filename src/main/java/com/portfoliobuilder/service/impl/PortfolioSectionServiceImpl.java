package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.PortfolioSectionDAO;
import com.portfoliobuilder.dao.impl.PortfolioSectionDAOImpl;
import com.portfoliobuilder.model.PortfolioSection;
import com.portfoliobuilder.service.PortfolioSectionService;
import com.portfoliobuilder.service.PortfolioService;

import java.sql.SQLException;
import java.util.List;

public class PortfolioSectionServiceImpl implements PortfolioSectionService {

    private final PortfolioSectionDAO portfolioSectionDAO = new PortfolioSectionDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public List<PortfolioSection> getSections(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return portfolioSectionDAO.findByPortfolioId(portfolioId);
    }

    @Override
    public void toggleSection(int sectionId, int portfolioId, int requestingUserId, boolean enabled) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        portfolioSectionDAO.setEnabled(sectionId, portfolioId, enabled);
    }

    @Override
    public void reorderSections(int portfolioId, int requestingUserId, List<Integer> orderedSectionIds) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        portfolioSectionDAO.reorderSections(portfolioId, orderedSectionIds);
    }
    
    @Override
    public List<PortfolioSection> getSectionsPublic(int portfolioId) throws SQLException {
        return portfolioSectionDAO.findByPortfolioId(portfolioId).stream()
                .filter(PortfolioSection::isEnabled)
                .toList();
    }
}