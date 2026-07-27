package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectDAO {

    Project create(Project project) throws SQLException;

    Optional<Project> findById(int projectId) throws SQLException;

    List<Project> findByPortfolioId(int portfolioId) throws SQLException;

    void update(Project project, int portfolioId) throws SQLException;

    void delete(int projectId, int portfolioId) throws SQLException;

    void reorder(int portfolioId, List<Integer> orderedProjectIds) throws SQLException;
}