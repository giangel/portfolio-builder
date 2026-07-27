package com.portfoliobuilder.service;

import com.portfoliobuilder.model.WorkExperience;

import java.sql.SQLException;
import java.util.List;

public interface WorkExperienceService {

    WorkExperience addExperience(int portfolioId, int requestingUserId, WorkExperience experience) throws SQLException;

    List<WorkExperience> getExperiences(int portfolioId, int requestingUserId) throws SQLException;

    void updateExperience(int experienceId, int portfolioId, int requestingUserId, WorkExperience experience) throws SQLException;

    void deleteExperience(int experienceId, int portfolioId, int requestingUserId) throws SQLException;

    void reorderExperiences(int portfolioId, int requestingUserId, List<Integer> orderedExperienceIds) throws SQLException;

    List<WorkExperience> getExperiencesPublic(int portfolioId) throws SQLException;
    
    
}