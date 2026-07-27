package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.PortfolioDAO;
import com.portfoliobuilder.model.Portfolio;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PortfolioDAOImpl implements PortfolioDAO {

    private static final String SELECT_BASE =
        "SELECT p.*, t.theme_name FROM portfolios p JOIN portfolio_themes t ON p.theme_id = t.theme_id ";

    @Override
    public Portfolio create(Portfolio portfolio) throws SQLException {
        String sql = "INSERT INTO portfolios (user_id, theme_id, title, slug, description, is_published) "
                + "VALUES (?, ?, ?, ?, ?, ?) RETURNING portfolio_id, created_at, updated_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolio.getUserId());
            ps.setInt(2, portfolio.getThemeId());
            ps.setString(3, portfolio.getTitle());
            ps.setString(4, portfolio.getSlug());
            ps.setString(5, portfolio.getDescription());
            ps.setBoolean(6, portfolio.isPublished());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    portfolio.setPortfolioId(rs.getInt("portfolio_id"));
                    portfolio.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    portfolio.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }
        return portfolio;
    }

    @Override
    public Optional<Portfolio> findById(int portfolioId) throws SQLException {
        String sql = SELECT_BASE + "WHERE p.portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Portfolio> findBySlug(String slug) throws SQLException {
        String sql = SELECT_BASE + "WHERE p.slug = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, slug);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsBySlug(String slug) throws SQLException {
        String sql = "SELECT 1 FROM portfolios WHERE slug = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, slug);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Portfolio> findByUserId(int userId) throws SQLException {
        String sql = SELECT_BASE + "WHERE p.user_id = ? ORDER BY p.updated_at DESC";
        List<Portfolio> portfolios = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    portfolios.add(mapRow(rs));
                }
            }
        }
        return portfolios;
    }

    @Override
    public List<Portfolio> findAllPublished() throws SQLException {
        String sql = SELECT_BASE + "WHERE p.is_published = TRUE ORDER BY p.published_at DESC";
        List<Portfolio> portfolios = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                portfolios.add(mapRow(rs));
            }
        }
        return portfolios;
    }

    @Override
    public List<Portfolio> findAll() throws SQLException {
        String sql = SELECT_BASE + "ORDER BY p.created_at DESC";
        List<Portfolio> portfolios = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                portfolios.add(mapRow(rs));
            }
        }
        return portfolios;
    }

    @Override
    public void update(Portfolio portfolio) throws SQLException {
        String sql = "UPDATE portfolios SET theme_id = ?, title = ?, description = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolio.getThemeId());
            ps.setString(2, portfolio.getTitle());
            ps.setString(3, portfolio.getDescription());
            ps.setInt(4, portfolio.getPortfolioId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int portfolioId) throws SQLException {
        String sql = "DELETE FROM portfolios WHERE portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void setPublished(int portfolioId, boolean published) throws SQLException {
        String sql = "UPDATE portfolios SET is_published = ?, published_at = CASE WHEN ? THEN CURRENT_TIMESTAMP ELSE published_at END, "
                + "updated_at = CURRENT_TIMESTAMP WHERE portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, published);
            ps.setBoolean(2, published);
            ps.setInt(3, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public int countAll() throws SQLException {
        return countWithCondition(null);
    }

    @Override
    public int countPublished() throws SQLException {
        return countWithCondition("is_published = TRUE");
    }

    @Override
    public int countUnpublished() throws SQLException {
        return countWithCondition("is_published = FALSE");
    }

    @Override
    public int countByUserId(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM portfolios WHERE user_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    private int countWithCondition(String condition) throws SQLException {
        String sql = "SELECT COUNT(*) FROM portfolios" + (condition != null ? " WHERE " + condition : "");
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private Portfolio mapRow(ResultSet rs) throws SQLException {
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId(rs.getInt("portfolio_id"));
        portfolio.setUserId(rs.getInt("user_id"));
        portfolio.setThemeId(rs.getInt("theme_id"));
        portfolio.setThemeName(rs.getString("theme_name"));
        portfolio.setTitle(rs.getString("title"));
        portfolio.setSlug(rs.getString("slug"));
        portfolio.setDescription(rs.getString("description"));
        portfolio.setPublished(rs.getBoolean("is_published"));
        portfolio.setPublishedAt(rs.getTimestamp("published_at") != null ? rs.getTimestamp("published_at").toLocalDateTime() : null);
        portfolio.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        portfolio.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return portfolio;
    }
}