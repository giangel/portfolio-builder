package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.UserProfileDAO;
import com.portfoliobuilder.model.UserProfile;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserProfileDAOImpl implements UserProfileDAO {

    @Override
    public UserProfile create(UserProfile profile) throws SQLException {
        String sql = "INSERT INTO user_profiles (user_id, full_name, headline, profile_image_url, about_text, phone, location) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING profile_id, created_at, updated_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFullName());
            ps.setString(3, profile.getHeadline());
            ps.setString(4, profile.getProfileImageUrl());
            ps.setString(5, profile.getAboutText());
            ps.setString(6, profile.getPhone());
            ps.setString(7, profile.getLocation());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    profile.setProfileId(rs.getInt("profile_id"));
                    profile.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    profile.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }
        return profile;
    }

    @Override
    public Optional<UserProfile> findByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM user_profiles WHERE user_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(UserProfile profile) throws SQLException {
        String sql = "UPDATE user_profiles SET full_name = ?, headline = ?, profile_image_url = ?, about_text = ?, "
                + "phone = ?, location = ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, profile.getFullName());
            ps.setString(2, profile.getHeadline());
            ps.setString(3, profile.getProfileImageUrl());
            ps.setString(4, profile.getAboutText());
            ps.setString(5, profile.getPhone());
            ps.setString(6, profile.getLocation());
            ps.setInt(7, profile.getUserId());
            ps.executeUpdate();
        }
    }

    private UserProfile mapRow(ResultSet rs) throws SQLException {
        UserProfile profile = new UserProfile();
        profile.setProfileId(rs.getInt("profile_id"));
        profile.setUserId(rs.getInt("user_id"));
        profile.setFullName(rs.getString("full_name"));
        profile.setHeadline(rs.getString("headline"));
        profile.setProfileImageUrl(rs.getString("profile_image_url"));
        profile.setAboutText(rs.getString("about_text"));
        profile.setPhone(rs.getString("phone"));
        profile.setLocation(rs.getString("location"));
        profile.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        profile.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return profile;
    }
}