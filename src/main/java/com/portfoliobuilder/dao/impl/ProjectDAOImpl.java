package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.ProjectDAO;
import com.portfoliobuilder.model.Project;
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

public class ProjectDAOImpl implements ProjectDAO {

    @Override
    public Project create(Project project) throws SQLException {
        String sql = "INSERT INTO projects (portfolio_id, title, description, image_url, project_url, "
                + "repository_url, start_date, end_date, display_order) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "
                + "RETURNING project_id, created_at, updated_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, project.getPortfolioId());
            ps.setString(2, project.getTitle());
            ps.setString(3, project.getDescription());
            ps.setString(4, project.getImageUrl());
            ps.setString(5, project.getProjectUrl());
            ps.setString(6, project.getRepositoryUrl());
            setNullableDate(ps, 7, project.getStartDate());
            setNullableDate(ps, 8, project.getEndDate());
            ps.setInt(9, project.getDisplayOrder());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    project.setProjectId(rs.getInt("project_id"));
                    project.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    project.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }
        return project;
    }

    @Override
    public Optional<Project> findById(int projectId) throws SQLException {
        String sql = "SELECT * FROM projects WHERE project_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Project> findByPortfolioId(int portfolioId) throws SQLException {
        String sql = "SELECT * FROM projects WHERE portfolio_id = ? ORDER BY display_order";
        List<Project> list = new ArrayList<>();
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
    public void update(Project project, int portfolioId) throws SQLException {
        String sql = "UPDATE projects SET title = ?, description = ?, image_url = ?, project_url = ?, "
                + "repository_url = ?, start_date = ?, end_date = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE project_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, project.getTitle());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getImageUrl());
            ps.setString(4, project.getProjectUrl());
            ps.setString(5, project.getRepositoryUrl());
            setNullableDate(ps, 6, project.getStartDate());
            setNullableDate(ps, 7, project.getEndDate());
            ps.setInt(8, project.getProjectId());
            ps.setInt(9, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int projectId, int portfolioId) throws SQLException {
        String sql = "DELETE FROM projects WHERE project_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.setInt(2, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void reorder(int portfolioId, List<Integer> orderedProjectIds) throws SQLException {
        String sql = "UPDATE projects SET display_order = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE project_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int order = 1;
            for (Integer id : orderedProjectIds) {
                ps.setInt(1, order++);
                ps.setInt(2, id);
                ps.setInt(3, portfolioId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void setNullableDate(PreparedStatement ps, int index, java.time.LocalDate date) throws SQLException {
        if (date != null) {
            ps.setDate(index, Date.valueOf(date));
        } else {
            ps.setNull(index, Types.DATE);
        }
    }

    private Project mapRow(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectId(rs.getInt("project_id"));
        project.setPortfolioId(rs.getInt("portfolio_id"));
        project.setTitle(rs.getString("title"));
        project.setDescription(rs.getString("description"));
        project.setImageUrl(rs.getString("image_url"));
        project.setProjectUrl(rs.getString("project_url"));
        project.setRepositoryUrl(rs.getString("repository_url"));
        project.setStartDate(rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null);
        project.setEndDate(rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
        project.setDisplayOrder(rs.getInt("display_order"));
        project.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        project.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return project;
    }
}