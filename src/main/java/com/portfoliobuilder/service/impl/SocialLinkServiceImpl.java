package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.SocialLinkDAO;
import com.portfoliobuilder.dao.impl.SocialLinkDAOImpl;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.SocialLink;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.SocialLinkService;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class SocialLinkServiceImpl implements SocialLinkService {

    private final SocialLinkDAO socialLinkDAO = new SocialLinkDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public SocialLink addSocialLink(int portfolioId, int requestingUserId, SocialLink socialLink) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(socialLink);
        socialLink.setPortfolioId(portfolioId);
        socialLink.setDisplayOrder(socialLinkDAO.findByPortfolioId(portfolioId).size() + 1);
        return socialLinkDAO.create(socialLink);
    }

    @Override
    public List<SocialLink> getSocialLinks(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return socialLinkDAO.findByPortfolioId(portfolioId);
    }

    @Override
    public void updateSocialLink(int socialLinkId, int portfolioId, int requestingUserId, SocialLink socialLink) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(socialLink);
        socialLink.setSocialLinkId(socialLinkId);
        socialLinkDAO.update(socialLink, portfolioId);
    }

    @Override
    public void deleteSocialLink(int socialLinkId, int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        socialLinkDAO.delete(socialLinkId, portfolioId);
    }

    private void validate(SocialLink socialLink) {
        if (socialLink.getPlatform() == null) {
            throw new ValidationException("Platform is required.");
        }
        if (!ValidationUtil.isValidUrl(socialLink.getUrl())) {
            throw new ValidationException("Please enter a valid URL.");
        }
    }
    
    @Override
    public List<SocialLink> getSocialLinksPublic(int portfolioId) throws SQLException {
        return socialLinkDAO.findByPortfolioId(portfolioId);
    }
}