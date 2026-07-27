package com.portfoliobuilder.service;

import com.portfoliobuilder.model.Certification;

import java.sql.SQLException;
import java.util.List;

public interface CertificationService {

    Certification addCertification(int portfolioId, int requestingUserId, Certification certification) throws SQLException;

    List<Certification> getCertifications(int portfolioId, int requestingUserId) throws SQLException;

    void updateCertification(int certificationId, int portfolioId, int requestingUserId, Certification certification) throws SQLException;

    void deleteCertification(int certificationId, int portfolioId, int requestingUserId) throws SQLException;

    void reorderCertifications(int portfolioId, int requestingUserId, List<Integer> orderedCertificationIds) throws SQLException;

    List<Certification> getCertificationsPublic(int portfolioId) throws SQLException;
}