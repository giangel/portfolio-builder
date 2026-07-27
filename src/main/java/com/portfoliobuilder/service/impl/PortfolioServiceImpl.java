package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.PortfolioDAO;
import com.portfoliobuilder.dao.PortfolioSectionDAO;
import com.portfoliobuilder.dao.PortfolioSettingsDAO;
import com.portfoliobuilder.dao.impl.PortfolioDAOImpl;
import com.portfoliobuilder.dao.impl.PortfolioSectionDAOImpl;
import com.portfoliobuilder.dao.impl.PortfolioSettingsDAOImpl;
import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.UnauthorizedActionException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Portfolio;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.util.SlugUtil;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioDAO portfolioDAO = new PortfolioDAOImpl();
    private final PortfolioSectionDAO portfolioSectionDAO = new PortfolioSectionDAOImpl();
    private final PortfolioSettingsDAO portfolioSettingsDAO = new PortfolioSettingsDAOImpl();
    private final com.portfoliobuilder.dao.AuditLogDAO auditLogDAO = new com.portfoliobuilder.dao.impl.AuditLogDAOImpl();

    @Override
    public Portfolio createPortfolio(int userId, String title, String description, int themeId) throws SQLException {
        if (ValidationUtil.isBlank(title)) {
            throw new ValidationException("Portfolio title is required.");
        }
        if (ValidationUtil.exceedsLength(title, 150)) {
            throw new ValidationException("Portfolio title is too long.");
        }

        Portfolio portfolio = new Portfolio();
        portfolio.setUserId(userId);
        portfolio.setThemeId(themeId);
        portfolio.setTitle(title.trim());
        portfolio.setDescription(description);
        portfolio.setSlug(generateUniqueSlug(title));
        portfolio.setPublished(false);

        Portfolio created = portfolioDAO.create(portfolio);
        portfolioSectionDAO.createDefaultSections(created.getPortfolioId());
        portfolioSettingsDAO.createDefault(created.getPortfolioId());
        return created;
    }

    @Override
    public Portfolio getOwnedPortfolio(int portfolioId, int requestingUserId) throws SQLException {
        Portfolio portfolio = portfolioDAO.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found."));
        if (portfolio.getUserId() != requestingUserId) {
            throw new UnauthorizedActionException("You do not have permission to access this portfolio.");
        }
        return portfolio;
    }

    @Override
    public Portfolio getPublicPortfolioBySlug(String slug) throws SQLException {
        Portfolio portfolio = portfolioDAO.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found."));
        if (!portfolio.isPublished()) {
            throw new ResourceNotFoundException("This portfolio is not currently published.");
        }
        return portfolio;
    }

    @Override
    public List<Portfolio> getPortfoliosForUser(int userId) throws SQLException {
        return portfolioDAO.findByUserId(userId);
    }

    @Override
    public void updatePortfolio(int portfolioId, int requestingUserId, String title, String description, int themeId) throws SQLException {
        Portfolio portfolio = getOwnedPortfolio(portfolioId, requestingUserId);
        if (ValidationUtil.isBlank(title)) {
            throw new ValidationException("Portfolio title is required.");
        }
        portfolio.setTitle(title.trim());
        portfolio.setDescription(description);
        portfolio.setThemeId(themeId);
        portfolioDAO.update(portfolio);
    }

    @Override
    public void deletePortfolio(int portfolioId, int requestingUserId) throws SQLException {
        getOwnedPortfolio(portfolioId, requestingUserId);
        portfolioDAO.delete(portfolioId);
        auditLogDAO.log(requestingUserId, "DELETE_PORTFOLIO", "portfolio_id=" + portfolioId);
    }

    @Override
    public void publish(int portfolioId, int requestingUserId) throws SQLException {
        getOwnedPortfolio(portfolioId, requestingUserId);
        portfolioDAO.setPublished(portfolioId, true);
        auditLogDAO.log(requestingUserId, "PUBLISH_PORTFOLIO", "portfolio_id=" + portfolioId);
    }

    @Override
    public void unpublish(int portfolioId, int requestingUserId) throws SQLException {
        getOwnedPortfolio(portfolioId, requestingUserId);
        portfolioDAO.setPublished(portfolioId, false);
        auditLogDAO.log(requestingUserId, "UNPUBLISH_PORTFOLIO", "portfolio_id=" + portfolioId);
    }

    @Override
    public List<Portfolio> getAllPortfolios() throws SQLException {
        return portfolioDAO.findAll();
    }

    @Override
    public int countAllPortfolios() throws SQLException {
        return portfolioDAO.countAll();
    }

    @Override
    public int countPublishedPortfolios() throws SQLException {
        return portfolioDAO.countPublished();
    }

    @Override
    public int countUnpublishedPortfolios() throws SQLException {
        return portfolioDAO.countUnpublished();
    }

    private String generateUniqueSlug(String title) throws SQLException {
        String baseSlug = SlugUtil.toBaseSlug(title);
        String candidate = baseSlug;
        int suffix = 2;
        while (portfolioDAO.existsBySlug(candidate)) {
            candidate = SlugUtil.appendSuffix(baseSlug, suffix);
            suffix++;
        }
        return candidate;
    }
}