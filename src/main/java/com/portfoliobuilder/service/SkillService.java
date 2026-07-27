package com.portfoliobuilder.service;

import com.portfoliobuilder.model.ProficiencyLevel;
import com.portfoliobuilder.model.Skill;

import java.sql.SQLException;
import java.util.List;

public interface SkillService {

    Skill addSkill(int portfolioId, int requestingUserId, String skillName, ProficiencyLevel level) throws SQLException;

    List<Skill> getSkills(int portfolioId, int requestingUserId) throws SQLException;

    void updateSkill(int skillId, int portfolioId, int requestingUserId, String skillName, ProficiencyLevel level) throws SQLException;

    void deleteSkill(int skillId, int portfolioId, int requestingUserId) throws SQLException;

    void reorderSkills(int portfolioId, int requestingUserId, List<Integer> orderedSkillIds) throws SQLException;

    List<Skill> getSkillsPublic(int portfolioId) throws SQLException;

}