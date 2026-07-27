package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.PortfolioViewDAO;
import com.portfoliobuilder.model.PortfolioViewRecord;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PortfolioViewDAOImpl implements PortfolioViewDAO {

    @Override
    public void recordView(int portfolioId, String ipAddress, String userAgent) throws SQLException {
        String sql = "INSERT INTO portfolio_views (portfolio_id, ip_address, user_agent) VALUES (?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            ps.setString(2, ipAddress);
            ps.setString(3, userAgent);
            ps.executeUpdate();
        }
    }

    @Override
    public int countViewsForPortfolio(int portfolioId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM portfolio_views WHERE portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    @Override
    public List<PortfolioViewRecord> findRecentForPortfolio(int portfolioId, int limit) throws SQLException {
        String sql = "SELECT * FROM portfolio_views WHERE portfolio_id = ? ORDER BY viewed_at DESC LIMIT ?";
        List<PortfolioViewRecord> views = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    views.add(mapRow(rs));
                }
            }
        }
        return views;
    }

    private PortfolioViewRecord mapRow(ResultSet rs) throws SQLException {
        PortfolioViewRecord view = new PortfolioViewRecord();
        view.setViewId(rs.getInt("view_id"));
        view.setPortfolioId(rs.getInt("portfolio_id"));
        view.setViewedAt(rs.getTimestamp("viewed_at").toLocalDateTime());
        view.setIpAddress(rs.getString("ip_address"));
        view.setUserAgent(rs.getString("user_agent"));
        return view;
    }
}