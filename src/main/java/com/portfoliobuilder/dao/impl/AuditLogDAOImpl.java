package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.AuditLogDAO;
import com.portfoliobuilder.model.AuditLog;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAOImpl implements AuditLogDAO {

    @Override
    public void log(Integer userId, String action, String details) throws SQLException {
        String sql = "INSERT INTO audit_logs (user_id, action, details) VALUES (?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (userId != null) {
                ps.setInt(1, userId);
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString(2, action);
            ps.setString(3, details);
            ps.executeUpdate();
        }
    }

    @Override
    public List<AuditLog> findRecent(int limit) throws SQLException {
        String sql = "SELECT * FROM audit_logs ORDER BY created_at DESC LIMIT ?";
        List<AuditLog> logs = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapRow(rs));
                }
            }
        }
        return logs;
    }

    private AuditLog mapRow(ResultSet rs) throws SQLException {
        AuditLog log = new AuditLog();
        log.setLogId(rs.getInt("log_id"));
        int userId = rs.getInt("user_id");
        log.setUserId(rs.wasNull() ? null : userId);
        log.setAction(rs.getString("action"));
        log.setDetails(rs.getString("details"));
        log.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return log;
    }
}