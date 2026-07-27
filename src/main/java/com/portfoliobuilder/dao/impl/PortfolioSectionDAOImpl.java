package com.portfoliobuilder.dao.impl;

import com.portfoliobuilder.dao.PortfolioSectionDAO;
import com.portfoliobuilder.model.PortfolioSection;
import com.portfoliobuilder.model.SectionType;
import com.portfoliobuilder.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PortfolioSectionDAOImpl implements PortfolioSectionDAO {

    private static final SectionType[] DEFAULT_ENABLED = {
        SectionType.HERO, SectionType.ABOUT, SectionType.SKILLS, SectionType.EXPERIENCE,
        SectionType.EDUCATION, SectionType.PROJECTS, SectionType.CONTACT
    };
    private static final SectionType[] DEFAULT_DISABLED = {
        SectionType.CERTIFICATIONS, SectionType.SERVICES, SectionType.TESTIMONIALS, SectionType.SOCIAL_LINKS
    };

    @Override
    public void createDefaultSections(int portfolioId) throws SQLException {
        String sql = "INSERT INTO portfolio_sections (portfolio_id, section_type, display_order, is_enabled) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int order = 1;
            for (SectionType type : DEFAULT_ENABLED) {
                ps.setInt(1, portfolioId);
                ps.setString(2, type.name());
                ps.setInt(3, order++);
                ps.setBoolean(4, true);
                ps.addBatch();
            }
            for (SectionType type : DEFAULT_DISABLED) {
                ps.setInt(1, portfolioId);
                ps.setString(2, type.name());
                ps.setInt(3, order++);
                ps.setBoolean(4, false);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public List<PortfolioSection> findByPortfolioId(int portfolioId) throws SQLException {
        String sql = "SELECT * FROM portfolio_sections WHERE portfolio_id = ? ORDER BY display_order";
        List<PortfolioSection> sections = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    sections.add(mapRow(rs));
                }
            }
        }
        return sections;
    }

    @Override
    public void setEnabled(int sectionId, int portfolioId, boolean enabled) throws SQLException {
        String sql = "UPDATE portfolio_sections SET is_enabled = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE section_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, enabled);
            ps.setInt(2, sectionId);
            ps.setInt(3, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void updateOrder(int sectionId, int portfolioId, int newDisplayOrder) throws SQLException {
        String sql = "UPDATE portfolio_sections SET display_order = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE section_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newDisplayOrder);
            ps.setInt(2, sectionId);
            ps.setInt(3, portfolioId);
            ps.executeUpdate();
        }
    }

    @Override
    public void reorderSections(int portfolioId, List<Integer> orderedSectionIds) throws SQLException {
        String sql = "UPDATE portfolio_sections SET display_order = ?, updated_at = CURRENT_TIMESTAMP "
                + "WHERE section_id = ? AND portfolio_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int order = 1;
            for (Integer sectionId : orderedSectionIds) {
                ps.setInt(1, order++);
                ps.setInt(2, sectionId);
                ps.setInt(3, portfolioId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public boolean existsForPortfolioAndType(int portfolioId, SectionType type) throws SQLException {
        String sql = "SELECT 1 FROM portfolio_sections WHERE portfolio_id = ? AND section_type = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portfolioId);
            ps.setString(2, type.name());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private PortfolioSection mapRow(ResultSet rs) throws SQLException {
        PortfolioSection section = new PortfolioSection();
        section.setSectionId(rs.getInt("section_id"));
        section.setPortfolioId(rs.getInt("portfolio_id"));
        section.setSectionType(SectionType.valueOf(rs.getString("section_type")));
        section.setDisplayOrder(rs.getInt("display_order"));
        section.setEnabled(rs.getBoolean("is_enabled"));
        section.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        section.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return section;
    }
}