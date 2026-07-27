package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.ProjectDAO;
import com.portfoliobuilder.dao.impl.ProjectDAOImpl;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Project;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.ProjectService;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class ProjectServiceImpl implements ProjectService {

    private final ProjectDAO projectDAO = new ProjectDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public Project addProject(int portfolioId, int requestingUserId, Project project) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(project);
        project.setPortfolioId(portfolioId);
        project.setDisplayOrder(projectDAO.findByPortfolioId(portfolioId).size() + 1);
        return projectDAO.create(project);
    }

    @Override
    public List<Project> getProjects(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return projectDAO.findByPortfolioId(portfolioId);
    }

    @Override
    public void updateProject(int projectId, int portfolioId, int requestingUserId, Project project) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(project);
        project.setProjectId(projectId);
        projectDAO.update(project, portfolioId);
    }

    @Override
    public void deleteProject(int projectId, int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        projectDAO.delete(projectId, portfolioId);
    }

    @Override
    public void reorderProjects(int portfolioId, int requestingUserId, List<Integer> orderedProjectIds) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        projectDAO.reorder(portfolioId, orderedProjectIds);
    }

    private void validate(Project project) {
        if (ValidationUtil.isBlank(project.getTitle())) {
            throw new ValidationException("Project title is required.");
        }
        if (project.getProjectUrl() != null && !project.getProjectUrl().isEmpty() && !ValidationUtil.isValidUrl(project.getProjectUrl())) {
            throw new ValidationException("Project URL must be a valid web address.");
        }
        if (project.getRepositoryUrl() != null && !project.getRepositoryUrl().isEmpty() && !ValidationUtil.isValidUrl(project.getRepositoryUrl())) {
            throw new ValidationException("Repository URL must be a valid web address.");
        }
    }
    
    @Override
    public List<Project> getProjectsPublic(int portfolioId) throws SQLException {
        return projectDAO.findByPortfolioId(portfolioId);
    }
}