package com.portfoliobuilder.service;

import com.portfoliobuilder.model.User;
import com.portfoliobuilder.model.UserProfile;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    User getUserById(int userId) throws SQLException;

    UserProfile getProfile(int userId) throws SQLException;

    void updateProfile(int userId, UserProfile updatedProfile) throws SQLException;

    void changePassword(int userId, String currentPlainPassword, String newPlainPassword) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    void setUserActive(int userId, boolean active) throws SQLException;

    int countAllUsers() throws SQLException;

    int countPortfolioUsers() throws SQLException;
}