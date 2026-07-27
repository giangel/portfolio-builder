package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.PortfolioSection;
import com.portfoliobuilder.model.SectionType;

import java.sql.SQLException;
import java.util.List;

public interface PortfolioSectionDAO {

    void createDefaultSections(int portfolioId) throws SQLException;

    List<PortfolioSection> findByPortfolioId(int portfolioId) throws SQLException;

    void setEnabled(int sectionId, int portfolioId, boolean enabled) throws SQLException;

    void updateOrder(int sectionId, int portfolioId, int newDisplayOrder) throws SQLException;

    void reorderSections(int portfolioId, List<Integer> orderedSectionIds) throws SQLException;

    boolean existsForPortfolioAndType(int portfolioId, SectionType type) throws SQLException;
}