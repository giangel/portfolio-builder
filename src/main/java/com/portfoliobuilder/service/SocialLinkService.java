package com.portfoliobuilder.service;

import com.portfoliobuilder.model.SocialLink;

import java.sql.SQLException;
import java.util.List;

public interface SocialLinkService {

    SocialLink addSocialLink(int portfolioId, int requestingUserId, SocialLink socialLink) throws SQLException;

    List<SocialLink> getSocialLinks(int portfolioId, int requestingUserId) throws SQLException;

    void updateSocialLink(int socialLinkId, int portfolioId, int requestingUserId, SocialLink socialLink) throws SQLException;

    void deleteSocialLink(int socialLinkId, int portfolioId, int requestingUserId) throws SQLException;

    List<SocialLink> getSocialLinksPublic(int portfolioId) throws SQLException;
}