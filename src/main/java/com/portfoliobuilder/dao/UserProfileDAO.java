package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.UserProfile;

import java.sql.SQLException;
import java.util.Optional;

public interface UserProfileDAO {

    UserProfile create(UserProfile profile) throws SQLException;

    Optional<UserProfile> findByUserId(int userId) throws SQLException;

    void update(UserProfile profile) throws SQLException;
}