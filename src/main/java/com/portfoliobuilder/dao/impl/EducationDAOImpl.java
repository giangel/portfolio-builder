package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.EducationDAO;
import com.portfoliobuilder.model.Education;
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

public class EducationDAOImpl implements EducationDAO {

    @Override
    public Education create(Education education) throws SQLException {
        String sql = "INSERT INTO educations (portfolio_id, institution_name, degree, field_of_study, start_date, "
                + "end_date, description, display_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
                + "RETURNING education_id, created_at, updated_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, education.getPortfolioId());
            ps.setString(2, education.getInstitutionName());
            ps.setString(3, education.getDegree());
            ps.setString(4, education.getFieldOfStudy());
            ps.setDate(5, Date.valueOf(education.getStartDate()));
            if (education.getEndDate() != null) {
                ps.setDate(6, Date.valueOf(education.getEndDate()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            ps.setString(7, education.getDescription());
            ps.setInt(8, education.getDisplayOrder());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    education.setEducationId(rs.getInt("education_id"));
                    education.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    education.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }
        return education;
    }

    @Override
    public Optional<Education> findById(int educationId) throws SQLException {
        String sql = "SELECT * FROM educations WHERE education_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, educationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Education> findByPortfolioId(int portfolioId) throws SQLException {
        String sql = "SELECT * FROM educations WHERE portfolio_id = ? ORDER BY display_order";
        List<Education> list = new ArrayList<>();
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
    public void update(Education education, int portfolioId) throws SQLException {
        String sql = "UPDATE educations SET institution_name = ?, degree = ?, field_of_study = ?, start_date = ?, "
                + "end_date = ?, description = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE education_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, education.getInstitutionName());
            ps.setString(2, education.getDegree());
            ps.setString(3, education.getFieldOfStudy());
            ps.setDate(4, Date.valueOf(education.getStartDate()));
            if (education.getEndDate() != null) {
                ps.setDate(5, Date.valueOf(education.getEndDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, education.getDescription());
            ps.setInt(7, education.getEducationId());
            ps.setInt(8, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int educationId, int portfolioId) throws SQLException {
        String sql = "DELETE FROM educations WHERE education_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, educationId);
            ps.setInt(2, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void reorder(int portfolioId, List<Integer> orderedEducationIds) throws SQLException {
        String sql = "UPDATE educations SET display_order = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE education_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int order = 1;
            for (Integer id : orderedEducationIds) {
                ps.setInt(1, order++);
                ps.setInt(2, id);
                ps.setInt(3, portfolioId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private Education mapRow(ResultSet rs) throws SQLException {
        Education education = new Education();
        education.setEducationId(rs.getInt("education_id"));
        education.setPortfolioId(rs.getInt("portfolio_id"));
        education.setInstitutionName(rs.getString("institution_name"));
        education.setDegree(rs.getString("degree"));
        education.setFieldOfStudy(rs.getString("field_of_study"));
        education.setStartDate(rs.getDate("start_date").toLocalDate());
        education.setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
        education.setDescription(rs.getString("description"));
        education.setDisplayOrder(rs.getInt("display_order"));
        education.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        education.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return education;
    }
}