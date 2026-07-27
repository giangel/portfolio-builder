package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.SkillDAO;
import com.portfoliobuilder.model.ProficiencyLevel;
import com.portfoliobuilder.model.Skill;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillDAOImpl implements SkillDAO {

    @Override
    public Skill create(Skill skill) throws SQLException {
        String sql = "INSERT INTO skills (portfolio_id, skill_name, proficiency_level, display_order) "
                + "VALUES (?, ?, ?, ?) RETURNING skill_id, created_at, updated_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, skill.getPortfolioId());
            ps.setString(2, skill.getSkillName());
            ps.setString(3, skill.getProficiencyLevel().name());
            ps.setInt(4, skill.getDisplayOrder());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    skill.setSkillId(rs.getInt("skill_id"));
                    skill.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    skill.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }
        return skill;
    }

    @Override
    public Optional<Skill> findById(int skillId) throws SQLException {
        String sql = "SELECT * FROM skills WHERE skill_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, skillId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Skill> findByPortfolioId(int portfolioId) throws SQLException {
        String sql = "SELECT * FROM skills WHERE portfolio_id = ? ORDER BY display_order";
        List<Skill> skills = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    skills.add(mapRow(rs));
                }
            }
        }
        return skills;
    }

    @Override
    public void update(Skill skill, int portfolioId) throws SQLException {
        String sql = "UPDATE skills SET skill_name = ?, proficiency_level = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE skill_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, skill.getSkillName());
            ps.setString(2, skill.getProficiencyLevel().name());
            ps.setInt(3, skill.getSkillId());
            ps.setInt(4, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int skillId, int portfolioId) throws SQLException {
        String sql = "DELETE FROM skills WHERE skill_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, skillId);
            ps.setInt(2, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void reorder(int portfolioId, List<Integer> orderedSkillIds) throws SQLException {
        String sql = "UPDATE skills SET display_order = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE skill_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int order = 1;
            for (Integer skillId : orderedSkillIds) {
                ps.setInt(1, order++);
                ps.setInt(2, skillId);
                ps.setInt(3, portfolioId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private Skill mapRow(ResultSet rs) throws SQLException {
        Skill skill = new Skill();
        skill.setSkillId(rs.getInt("skill_id"));
        skill.setPortfolioId(rs.getInt("portfolio_id"));
        skill.setSkillName(rs.getString("skill_name"));
        skill.setProficiencyLevel(ProficiencyLevel.valueOf(rs.getString("proficiency_level")));
        skill.setDisplayOrder(rs.getInt("display_order"));
        skill.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        skill.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return skill;
    }
}