package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.CertificationDAO;
import com.portfoliobuilder.model.Certification;
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

public class CertificationDAOImpl implements CertificationDAO {

    @Override
    public Certification create(Certification certification) throws SQLException {
        String sql = "INSERT INTO certifications (portfolio_id, certification_name, issuing_organization, "
                + "issue_date, expiration_date, credential_url, display_order) VALUES (?, ?, ?, ?, ?, ?, ?) "
                + "RETURNING certification_id, created_at, updated_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, certification.getPortfolioId());
            ps.setString(2, certification.getCertificationName());
            ps.setString(3, certification.getIssuingOrganization());
            ps.setDate(4, Date.valueOf(certification.getIssueDate()));
            if (certification.getExpirationDate() != null) {
                ps.setDate(5, Date.valueOf(certification.getExpirationDate()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, certification.getCredentialUrl());
            ps.setInt(7, certification.getDisplayOrder());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    certification.setCertificationId(rs.getInt("certification_id"));
                    certification.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    certification.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                }
            }
        }
        return certification;
    }

    @Override
    public Optional<Certification> findById(int certificationId) throws SQLException {
        String sql = "SELECT * FROM certifications WHERE certification_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, certificationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Certification> findByPortfolioId(int portfolioId) throws SQLException {
        String sql = "SELECT * FROM certifications WHERE portfolio_id = ? ORDER BY display_order";
        List<Certification> list = new ArrayList<>();
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
    public void update(Certification certification, int portfolioId) throws SQLException {
        String sql = "UPDATE certifications SET certification_name = ?, issuing_organization = ?, issue_date = ?, "
                + "expiration_date = ?, credential_url = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE certification_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, certification.getCertificationName());
            ps.setString(2, certification.getIssuingOrganization());
            ps.setDate(3, Date.valueOf(certification.getIssueDate()));
            if (certification.getExpirationDate() != null) {
                ps.setDate(4, Date.valueOf(certification.getExpirationDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, certification.getCredentialUrl());
            ps.setInt(6, certification.getCertificationId());
            ps.setInt(7, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int certificationId, int portfolioId) throws SQLException {
        String sql = "DELETE FROM certifications WHERE certification_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, certificationId);
            ps.setInt(2, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void reorder(int portfolioId, List<Integer> orderedCertificationIds) throws SQLException {
        String sql = "UPDATE certifications SET display_order = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE certification_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int order = 1;
            for (Integer id : orderedCertificationIds) {
                ps.setInt(1, order++);
                ps.setInt(2, id);
                ps.setInt(3, portfolioId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private Certification mapRow(ResultSet rs) throws SQLException {
        Certification certification = new Certification();
        certification.setCertificationId(rs.getInt("certification_id"));
        certification.setPortfolioId(rs.getInt("portfolio_id"));
        certification.setCertificationName(rs.getString("certification_name"));
        certification.setIssuingOrganization(rs.getString("issuing_organization"));
        certification.setIssueDate(rs.getDate("issue_date").toLocalDate());
        certification.setExpirationDate(rs.getDate("expiration_date") != null ? rs.getDate("expiration_date").toLocalDate() : null);
        certification.setCredentialUrl(rs.getString("credential_url"));
        certification.setDisplayOrder(rs.getInt("display_order"));
        certification.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        certification.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return certification;
    }
}