package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.PortfolioThemeDAO;
import com.portfoliobuilder.model.PortfolioTheme;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PortfolioThemeDAOImpl implements PortfolioThemeDAO {

    @Override
    public List<PortfolioTheme> findAll() throws SQLException {
        String sql = "SELECT * FROM portfolio_themes ORDER BY theme_name";
        List<PortfolioTheme> themes = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                themes.add(mapRow(rs));
            }
        }
        return themes;
    }

    @Override
    public List<PortfolioTheme> findActive() throws SQLException {
        String sql = "SELECT * FROM portfolio_themes WHERE is_active = TRUE ORDER BY theme_name";
        List<PortfolioTheme> themes = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                themes.add(mapRow(rs));
            }
        }
        return themes;
    }

    @Override
    public Optional<PortfolioTheme> findById(int themeId) throws SQLException {
        String sql = "SELECT * FROM portfolio_themes WHERE theme_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, themeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<PortfolioTheme> findByKey(String themeKey) throws SQLException {
        String sql = "SELECT * FROM portfolio_themes WHERE theme_key = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, themeKey);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void setActive(int themeId, boolean active) throws SQLException {
        String sql = "UPDATE portfolio_themes SET is_active = ? WHERE theme_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setInt(2, themeId);
            ps.executeUpdate();
        }
    }

    private PortfolioTheme mapRow(ResultSet rs) throws SQLException {
        PortfolioTheme theme = new PortfolioTheme();
        theme.setThemeId(rs.getInt("theme_id"));
        theme.setThemeName(rs.getString("theme_name"));
        theme.setThemeKey(rs.getString("theme_key"));
        theme.setDescription(rs.getString("description"));
        theme.setLayoutFamily(rs.getString("layout_family"));
        theme.setActive(rs.getBoolean("is_active"));
        theme.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return theme;
    }
}