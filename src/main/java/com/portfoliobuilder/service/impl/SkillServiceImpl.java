package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.SkillDAO;
import com.portfoliobuilder.dao.impl.SkillDAOImpl;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.ProficiencyLevel;
import com.portfoliobuilder.model.Skill;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.service.SkillService;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class SkillServiceImpl implements SkillService {

    private final SkillDAO skillDAO = new SkillDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public Skill addSkill(int portfolioId, int requestingUserId, String skillName, ProficiencyLevel level) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        if (ValidationUtil.isBlank(skillName)) {
            throw new ValidationException("Skill name is required.");
        }
        Skill skill = new Skill();
        skill.setPortfolioId(portfolioId);
        skill.setSkillName(skillName.trim());
        skill.setProficiencyLevel(level != null ? level : ProficiencyLevel.INTERMEDIATE);
        skill.setDisplayOrder(skillDAO.findByPortfolioId(portfolioId).size() + 1);
        return skillDAO.create(skill);
    }

    @Override
    public List<Skill> getSkills(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return skillDAO.findByPortfolioId(portfolioId);
    }

    @Override
    public void updateSkill(int skillId, int portfolioId, int requestingUserId, String skillName, ProficiencyLevel level) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        if (ValidationUtil.isBlank(skillName)) {
            throw new ValidationException("Skill name is required.");
        }
        Skill skill = new Skill();
        skill.setSkillId(skillId);
        skill.setSkillName(skillName.trim());
        skill.setProficiencyLevel(level);
        skillDAO.update(skill, portfolioId);
    }

    @Override
    public void deleteSkill(int skillId, int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        skillDAO.delete(skillId, portfolioId);
    }

    @Override
    public void reorderSkills(int portfolioId, int requestingUserId, List<Integer> orderedSkillIds) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        skillDAO.reorder(portfolioId, orderedSkillIds);
    }
    
    @Override
    public List<Skill> getSkillsPublic(int portfolioId) throws SQLException {
        return skillDAO.findByPortfolioId(portfolioId);
    }
    
}