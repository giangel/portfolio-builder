package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.CertificationDAO;
import com.portfoliobuilder.dao.impl.CertificationDAOImpl;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.Certification;
import com.portfoliobuilder.service.CertificationService;
import com.portfoliobuilder.service.PortfolioService;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class CertificationServiceImpl implements CertificationService {

    private final CertificationDAO certificationDAO = new CertificationDAOImpl();
    private final PortfolioService portfolioService = new PortfolioServiceImpl();

    @Override
    public Certification addCertification(int portfolioId, int requestingUserId, Certification certification) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(certification);
        certification.setPortfolioId(portfolioId);
        certification.setDisplayOrder(certificationDAO.findByPortfolioId(portfolioId).size() + 1);
        return certificationDAO.create(certification);
    }

    @Override
    public List<Certification> getCertifications(int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        return certificationDAO.findByPortfolioId(portfolioId);
    }

    @Override
    public void updateCertification(int certificationId, int portfolioId, int requestingUserId, Certification certification) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        validate(certification);
        certification.setCertificationId(certificationId);
        certificationDAO.update(certification, portfolioId);
    }

    @Override
    public void deleteCertification(int certificationId, int portfolioId, int requestingUserId) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        certificationDAO.delete(certificationId, portfolioId);
    }

    @Override
    public void reorderCertifications(int portfolioId, int requestingUserId, List<Integer> orderedCertificationIds) throws SQLException {
        portfolioService.getOwnedPortfolio(portfolioId, requestingUserId);
        certificationDAO.reorder(portfolioId, orderedCertificationIds);
    }

    private void validate(Certification certification) {
        if (ValidationUtil.isBlank(certification.getCertificationName()) || ValidationUtil.isBlank(certification.getIssuingOrganization())) {
            throw new ValidationException("Certification name and issuing organization are required.");
        }
        if (certification.getIssueDate() == null) {
            throw new ValidationException("Issue date is required.");
        }
    }
    
    @Override
    public List<Certification> getCertificationsPublic(int portfolioId) throws SQLException {
        return certificationDAO.findByPortfolioId(portfolioId);
    }
}