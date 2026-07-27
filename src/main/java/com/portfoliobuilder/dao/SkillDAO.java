package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.Skill;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SkillDAO {

    Skill create(Skill skill) throws SQLException;

    Optional<Skill> findById(int skillId) throws SQLException;

    List<Skill> findByPortfolioId(int portfolioId) throws SQLException;

    void update(Skill skill, int portfolioId) throws SQLException;

    void delete(int skillId, int portfolioId) throws SQLException;

    void reorder(int portfolioId, List<Integer> orderedSkillIds) throws SQLException;
}