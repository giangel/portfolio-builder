package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.ServiceDAO;
import com.portfoliobuilder.model.Service;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDAOImpl implements ServiceDAO {

    @Override
    public Service create(Service service) throws SQLException {
        String sql = "INSERT INTO services (portfolio_id, title, description, icon_key, display_order) "
                + "VALUES (?, ?, ?, ?, ?) RETURNING service_id, created_at, updated_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, service.getPortfolioId());
            ps.setString(2, service.getTitle());
            ps.setString(3, service.getDescription());
            ps.setString(4, service.getIconKey());
            ps.setInt(5, service.getDisplayOrder());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    service.setServiceId(rs.getInt("service_id"));
                    service.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    service.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }
        return service;
    }

    @Override
    public Optional<Service> findById(int serviceId) throws SQLException {
        String sql = "SELECT * FROM services WHERE service_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Service> findByPortfolioId(int portfolioId) throws SQLException {
        String sql = "SELECT * FROM services WHERE portfolio_id = ? ORDER BY display_order";
        List<Service> list = new ArrayList<>();
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
    public void update(Service service, int portfolioId) throws SQLException {
        String sql = "UPDATE services SET title = ?, description = ?, icon_key = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE service_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, service.getTitle());
            ps.setString(2, service.getDescription());
            ps.setString(3, service.getIconKey());
            ps.setInt(4, service.getServiceId());
            ps.setInt(5, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int serviceId, int portfolioId) throws SQLException {
        String sql = "DELETE FROM services WHERE service_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            ps.setInt(2, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void reorder(int portfolioId, List<Integer> orderedServiceIds) throws SQLException {
        String sql = "UPDATE services SET display_order = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE service_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int order = 1;
            for (Integer id : orderedServiceIds) {
                ps.setInt(1, order++);
                ps.setInt(2, id);
                ps.setInt(3, portfolioId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private Service mapRow(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setServiceId(rs.getInt("service_id"));
        service.setPortfolioId(rs.getInt("portfolio_id"));
        service.setTitle(rs.getString("title"));
        service.setDescription(rs.getString("description"));
        service.setIconKey(rs.getString("icon_key"));
        service.setDisplayOrder(rs.getInt("display_order"));
        service.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        service.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return service;
    }
}