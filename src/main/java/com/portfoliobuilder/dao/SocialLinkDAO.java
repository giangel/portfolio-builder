package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.SocialLink;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SocialLinkDAO {

    SocialLink create(SocialLink socialLink) throws SQLException;

    Optional<SocialLink> findById(int socialLinkId) throws SQLException;

    List<SocialLink> findByPortfolioId(int portfolioId) throws SQLException;

    void update(SocialLink socialLink, int portfolioId) throws SQLException;

    void delete(int socialLinkId, int portfolioId) throws SQLException;
}