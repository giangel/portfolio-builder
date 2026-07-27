package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.SocialLinkDAO;
import com.portfoliobuilder.model.SocialLink;
import com.portfoliobuilder.model.SocialPlatform;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SocialLinkDAOImpl implements SocialLinkDAO {

    @Override
    public SocialLink create(SocialLink socialLink) throws SQLException {
        String sql = "INSERT INTO social_links (portfolio_id, platform, url, display_order) VALUES (?, ?, ?, ?) "
                + "RETURNING social_link_id, created_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, socialLink.getPortfolioId());
            ps.setString(2, socialLink.getPlatform().name());
            ps.setString(3, socialLink.getUrl());
            ps.setInt(4, socialLink.getDisplayOrder());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    socialLink.setSocialLinkId(rs.getInt("social_link_id"));
                    socialLink.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                }
            }
        }
        return socialLink;
    }

    @Override
    public Optional<SocialLink> findById(int socialLinkId) throws SQLException {
        String sql = "SELECT * FROM social_links WHERE social_link_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, socialLinkId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<SocialLink> findByPortfolioId(int portfolioId) throws SQLException {
        String sql = "SELECT * FROM social_links WHERE portfolio_id = ? ORDER BY display_order";
        List<SocialLink> list = new ArrayList<>();
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
    public void update(SocialLink socialLink, int portfolioId) throws SQLException {
        String sql = "UPDATE social_links SET platform = ?, url = ? WHERE social_link_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, socialLink.getPlatform().name());
            ps.setString(2, socialLink.getUrl());
            ps.setInt(3, socialLink.getSocialLinkId());
            ps.setInt(4, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int socialLinkId, int portfolioId) throws SQLException {
        String sql = "DELETE FROM social_links WHERE social_link_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, socialLinkId);
            ps.setInt(2, portfolioId);
            ps.executeUpdate();
        }
    }

    private SocialLink mapRow(ResultSet rs) throws SQLException {
        SocialLink socialLink = new SocialLink();
        socialLink.setSocialLinkId(rs.getInt("social_link_id"));
        socialLink.setPortfolioId(rs.getInt("portfolio_id"));
        socialLink.setPlatform(SocialPlatform.valueOf(rs.getString("platform")));
        socialLink.setUrl(rs.getString("url"));
        socialLink.setDisplayOrder(rs.getInt("display_order"));
        socialLink.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return socialLink;
    }
}