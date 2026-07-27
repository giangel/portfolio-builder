package com.portfoliobuilder.service;

import com.portfoliobuilder.model.Education;

import java.sql.SQLException;
import java.util.List;

public interface EducationService {

    Education addEducation(int portfolioId, int requestingUserId, Education education) throws SQLException;

    List<Education> getEducations(int portfolioId, int requestingUserId) throws SQLException;

    void updateEducation(int educationId, int portfolioId, int requestingUserId, Education education) throws SQLException;

    void deleteEducation(int educationId, int portfolioId, int requestingUserId) throws SQLException;

    void reorderEducations(int portfolioId, int requestingUserId, List<Integer> orderedEducationIds) throws SQLException;

    List<Education> getEducationsPublic(int portfolioId) throws SQLException;
    
}