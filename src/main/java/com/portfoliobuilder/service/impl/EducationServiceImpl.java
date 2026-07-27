package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.EducationDAO;
import com.portfoliobuilder.dao.impl.EducationDAOImpl;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Education;
import com.portfoliobuilder.service.EducationService;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class EducationServiceImpl implements EducationService {

    private final EducationDAO educationDAO = new EducationDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public Education addEducation(int portfolioId, int requestingUserId, Education education) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(education);
        education.setPortfolioId(portfolioId);
        education.setDisplayOrder(educationDAO.findByPortfolioId(portfolioId).size() + 1);
        return educationDAO.create(education);
    }

    @Override
    public List<Education> getEducations(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return educationDAO.findByPortfolioId(portfolioId);
    }

    @Override
    public void updateEducation(int educationId, int portfolioId, int requestingUserId, Education education) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(education);
        education.setEducationId(educationId);
        educationDAO.update(education, portfolioId);
    }

    @Override
    public void deleteEducation(int educationId, int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        educationDAO.delete(educationId, portfolioId);
    }

    @Override
    public void reorderEducations(int portfolioId, int requestingUserId, List<Integer> orderedEducationIds) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        educationDAO.reorder(portfolioId, orderedEducationIds);
    }

    private void validate(Education education) {
        if (ValidationUtil.isBlank(education.getInstitutionName()) || ValidationUtil.isBlank(education.getDegree())) {
            throw new ValidationException("Institution name and degree are required.");
        }
        if (education.getStartDate() == null) {
            throw new ValidationException("Start date is required.");
        }
    }
    
    @Override
    public List<Education> getEducationsPublic(int portfolioId) throws SQLException {
        return educationDAO.findByPortfolioId(portfolioId);
    }
}