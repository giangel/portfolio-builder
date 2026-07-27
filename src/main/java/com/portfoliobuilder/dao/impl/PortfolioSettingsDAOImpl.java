package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.PortfolioSettingsDAO;
import com.portfoliobuilder.model.PortfolioSettings;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PortfolioSettingsDAOImpl implements PortfolioSettingsDAO {

    @Override
    public PortfolioSettings createDefault(int portfolioId) throws SQLException {
        String sql = "INSERT INTO portfolio_settings (portfolio_id) VALUES (?) RETURNING *";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        throw new SQLException("Failed to create default portfolio settings for portfolio_id " + portfolioId);
    }

    @Override
    public Optional<PortfolioSettings> findByPortfolioId(int portfolioId) throws SQLException {
        String sql = "SELECT * FROM portfolio_settings WHERE portfolio_id = ?";
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
    public void update(PortfolioSettings settings) throws SQLException {
        String sql = "UPDATE portfolio_settings SET accent_color = ?, typography_choice = ?, background_style = ?, "
                + "button_style = ?, layout_variant = ?, updated_at = CURRENT_TIMESTAMP WHERE portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, settings.getAccentColor());
            ps.setString(2, settings.getTypographyChoice());
            ps.setString(3, settings.getBackgroundStyle());
            ps.setString(4, settings.getButtonStyle());
            ps.setString(5, settings.getLayoutVariant());
            ps.setInt(6, settings.getPortfolioId());
            ps.executeUpdate();
        }
    }

    private PortfolioSettings mapRow(ResultSet rs) throws SQLException {
        PortfolioSettings settings = new PortfolioSettings();
        settings.setSettingId(rs.getInt("setting_id"));
        settings.setPortfolioId(rs.getInt("portfolio_id"));
        settings.setAccentColor(rs.getString("accent_color"));
        settings.setTypographyChoice(rs.getString("typography_choice"));
        settings.setBackgroundStyle(rs.getString("background_style"));
        settings.setButtonStyle(rs.getString("button_style"));
        settings.setLayoutVariant(rs.getString("layout_variant"));
        settings.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        settings.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return settings;
    }
}