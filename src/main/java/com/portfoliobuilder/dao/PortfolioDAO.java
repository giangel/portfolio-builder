package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.Portfolio;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PortfolioDAO {

    Portfolio create(Portfolio portfolio) throws SQLException;

    Optional<Portfolio> findById(int portfolioId) throws SQLException;

    Optional<Portfolio> findBySlug(String slug) throws SQLException;

    boolean existsBySlug(String slug) throws SQLException;

    List<Portfolio> findByUserId(int userId) throws SQLException;

    List<Portfolio> findAllPublished() throws SQLException;

    List<Portfolio> findAll() throws SQLException;

    void update(Portfolio portfolio) throws SQLException;

    void delete(int portfolioId) throws SQLException;

    void setPublished(int portfolioId, boolean published) throws SQLException;

    int countAll() throws SQLException;

    int countPublished() throws SQLException;

    int countUnpublished() throws SQLException;

    int countByUserId(int userId) throws SQLException;
}