package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.WorkExperienceDAO;
import com.portfoliobuilder.dao.impl.WorkExperienceDAOImpl;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.WorkExperience;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.WorkExperienceService;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceDAO workExperienceDAO = new WorkExperienceDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public WorkExperience addExperience(int portfolioId, int requestingUserId, WorkExperience experience) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(experience);
        experience.setPortfolioId(portfolioId);
        experience.setDisplayOrder(workExperienceDAO.findByPortfolioId(portfolioId).size() + 1);
        return workExperienceDAO.create(experience);
    }

    @Override
    public List<WorkExperience> getExperiences(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return workExperienceDAO.findByPortfolioId(portfolioId);
    }

    @Override
    public void updateExperience(int experienceId, int portfolioId, int requestingUserId, WorkExperience experience) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(experience);
        experience.setExperienceId(experienceId);
        workExperienceDAO.update(experience, portfolioId);
    }

    @Override
    public void deleteExperience(int experienceId, int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        workExperienceDAO.delete(experienceId, portfolioId);
    }

    @Override
    public void reorderExperiences(int portfolioId, int requestingUserId, List<Integer> orderedExperienceIds) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        workExperienceDAO.reorder(portfolioId, orderedExperienceIds);
    }

    private void validate(WorkExperience experience) {
        if (ValidationUtil.isBlank(experience.getJobTitle()) || ValidationUtil.isBlank(experience.getCompanyName())) {
            throw new ValidationException("Job title and company name are required.");
        }
        if (experience.getStartDate() == null) {
            throw new ValidationException("Start date is required.");
        }
        if (!experience.isCurrent() && experience.getEndDate() != null && experience.getEndDate().isBefore(experience.getStartDate())) {
            throw new ValidationException("End date cannot be before start date.");
        }
    }
    
    @Override
    public List<WorkExperience> getExperiencesPublic(int portfolioId) throws SQLException {
        return workExperienceDAO.findByPortfolioId(portfolioId);
    }
}