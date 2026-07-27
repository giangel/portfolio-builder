package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.Education;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EducationDAO {

    Education create(Education education) throws SQLException;

    Optional<Education> findById(int educationId) throws SQLException;

    List<Education> findByPortfolioId(int portfolioId) throws SQLException;

    void update(Education education, int portfolioId) throws SQLException;

    void delete(int educationId, int portfolioId) throws SQLException;

    void reorder(int portfolioId, List<Integer> orderedEducationIds) throws SQLException;
}