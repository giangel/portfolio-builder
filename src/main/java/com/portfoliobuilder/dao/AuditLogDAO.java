package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.AuditLog;

import java.sql.SQLException;
import java.util.List;

public interface AuditLogDAO {

    void log(Integer userId, String action, String details) throws SQLException;

    List<AuditLog> findRecent(int limit) throws SQLException;
}