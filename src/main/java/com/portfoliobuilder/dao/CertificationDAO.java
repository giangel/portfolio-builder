package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.Certification;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CertificationDAO {

    Certification create(Certification certification) throws SQLException;

    Optional<Certification> findById(int certificationId) throws SQLException;

    List<Certification> findByPortfolioId(int portfolioId) throws SQLException;

    void update(Certification certification, int portfolioId) throws SQLException;

    void delete(int certificationId, int portfolioId) throws SQLException;

    void reorder(int portfolioId, List<Integer> orderedCertificationIds) throws SQLException;
}