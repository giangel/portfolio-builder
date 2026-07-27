package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    User create(User user) throws SQLException;

    Optional<User> findById(int userId) throws SQLException;

    Optional<User> findByEmail(String email) throws SQLException;

    boolean existsByEmail(String email) throws SQLException;

    void updatePasswordHash(int userId, String newPasswordHash) throws SQLException;

    void setActive(int userId, boolean active) throws SQLException;

    List<User> findAll() throws SQLException;

    int countAll() throws SQLException;

    int countByRoleName(String roleName) throws SQLException;
}