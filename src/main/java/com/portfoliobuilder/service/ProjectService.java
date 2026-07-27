package com.portfoliobuilder.service;

import com.portfoliobuilder.model.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectService {

    Project addProject(int portfolioId, int requestingUserId, Project project) throws SQLException;

    List<Project> getProjects(int portfolioId, int requestingUserId) throws SQLException;

    void updateProject(int projectId, int portfolioId, int requestingUserId, Project project) throws SQLException;

    void deleteProject(int projectId, int portfolioId, int requestingUserId) throws SQLException;

    void reorderProjects(int portfolioId, int requestingUserId, List<Integer> orderedProjectIds) throws SQLException;

    List<Project> getProjectsPublic(int portfolioId) throws SQLException;
    
}