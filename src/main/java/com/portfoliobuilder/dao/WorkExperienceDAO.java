package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.WorkExperience;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface WorkExperienceDAO {

    WorkExperience create(WorkExperience experience) throws SQLException;

    Optional<WorkExperience> findById(int experienceId) throws SQLException;

    List<WorkExperience> findByPortfolioId(int portfolioId) throws SQLException;

    void update(WorkExperience experience, int portfolioId) throws SQLException;

    void delete(int experienceId, int portfolioId) throws SQLException;

    void reorder(int portfolioId, List<Integer> orderedExperienceIds) throws SQLException;
}