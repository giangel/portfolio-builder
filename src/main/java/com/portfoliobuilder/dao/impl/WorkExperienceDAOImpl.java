package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.WorkExperienceDAO;
import com.portfoliobuilder.model.WorkExperience;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkExperienceDAOImpl implements WorkExperienceDAO {

    @Override
    public WorkExperience create(WorkExperience experience) throws SQLException {
        String sql = "INSERT INTO work_experiences (portfolio_id, job_title, company_name, location, start_date, "
                + "end_date, is_current, description, display_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "RETURNING experience_id, created_at, updated_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, experience.getPortfolioId());
            ps.setString(2, experience.getJobTitle());
            ps.setString(3, experience.getCompanyName());
            ps.setString(4, experience.getLocation());
            ps.setDate(5, Date.valueOf(experience.getStartDate()));
            if (experience.getEndDate() != null) {
                ps.setDate(6, Date.valueOf(experience.getEndDate()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setBoolean(7, experience.isCurrent());
            ps.setString(8, experience.getDescription());
            ps.setInt(9, experience.getDisplayOrder());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    experience.setExperienceId(rs.getInt("experience_id"));
                    experience.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    experience.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }
        return experience;
    }

    @Override
    public Optional<WorkExperience> findById(int experienceId) throws SQLException {
        String sql = "SELECT * FROM work_experiences WHERE experience_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, experienceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<WorkExperience> findByPortfolioId(int portfolioId) throws SQLException {
        String sql = "SELECT * FROM work_experiences WHERE portfolio_id = ? ORDER BY display_order";
        List<WorkExperience> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    @Override
    public void update(WorkExperience experience, int portfolioId) throws SQLException {
        String sql = "UPDATE work_experiences SET job_title = ?, company_name = ?, location = ?, start_date = ?, "
                + "end_date = ?, is_current = ?, description = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE experience_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, experience.getJobTitle());
            ps.setString(2, experience.getCompanyName());
            ps.setString(3, experience.getLocation());
            ps.setDate(4, Date.valueOf(experience.getStartDate()));
            if (experience.getEndDate() != null) {
                ps.setDate(5, Date.valueOf(experience.getEndDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setBoolean(6, experience.isCurrent());
            ps.setString(7, experience.getDescription());
            ps.setInt(8, experience.getExperienceId());
            ps.setInt(9, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int experienceId, int portfolioId) throws SQLException {
        String sql = "DELETE FROM work_experiences WHERE experience_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, experienceId);
            ps.setInt(2, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void reorder(int portfolioId, List<Integer> orderedExperienceIds) throws SQLException {
        String sql = "UPDATE work_experiences SET display_order = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE experience_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int order = 1;
            for (Integer id : orderedExperienceIds) {
                ps.setInt(1, order++);
                ps.setInt(2, id);
                ps.setInt(3, portfolioId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private WorkExperience mapRow(ResultSet rs) throws SQLException {
        WorkExperience experience = new WorkExperience();
        experience.setExperienceId(rs.getInt("experience_id"));
        experience.setPortfolioId(rs.getInt("portfolio_id"));
        experience.setJobTitle(rs.getString("job_title"));
        experience.setCompanyName(rs.getString("company_name"));
        experience.setLocation(rs.getString("location"));
        experience.setStartDate(rs.getDate("start_date").toLocalDate());
        experience.setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
        experience.setCurrent(rs.getBoolean("is_current"));
        experience.setDescription(rs.getString("description"));
        experience.setDisplayOrder(rs.getInt("display_order"));
        experience.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        experience.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return experience;
    }
}