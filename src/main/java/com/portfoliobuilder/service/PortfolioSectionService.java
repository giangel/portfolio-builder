package com.portfoliobuilder.service;

import com.portfoliobuilder.model.PortfolioSection;

import java.sql.SQLException;
import java.util.List;

public interface PortfolioSectionService {

    List<PortfolioSection> getSections(int portfolioId, int requestingUserId) throws SQLException;

    void toggleSection(int sectionId, int portfolioId, int requestingUserId, boolean enabled) throws SQLException;

    void reorderSections(int portfolioId, int requestingUserId, List<Integer> orderedSectionIds) throws SQLException;
    
    List<PortfolioSection> getSectionsPublic(int portfolioId) throws SQLException;
}