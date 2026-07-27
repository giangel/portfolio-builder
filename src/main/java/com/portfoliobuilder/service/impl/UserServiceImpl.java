package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.UserDAO;
import com.portfoliobuilder.dao.UserProfileDAO;
import com.portfoliobuilder.dao.impl.UserDAOImpl;
import com.portfoliobuilder.dao.impl.UserProfileDAOImpl;
import com.portfoliobuilder.exception.AuthenticationException;
import com.portfoliobuilder.exception.ResourceNotFoundException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.User;
import com.portfoliobuilder.model.UserProfile;
import com.portfoliobuilder.service.UserService;
import com.portfoliobuilder.util.PasswordUtil;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAOImpl();
    private final UserProfileDAO userProfileDAO = new UserProfileDAOImpl();

    @Override
    public User getUserById(int userId) throws SQLException {
        return userDAO.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    public UserProfile getProfile(int userId) throws SQLException {
        return userProfileDAO.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found."));
    }

    @Override
    public void updateProfile(int userId, UserProfile updatedProfile) throws SQLException {
        if (ValidationUtil.isBlank(updatedProfile.getFullName())) {
            throw new ValidationException("Full name is required.");
        }
        if (ValidationUtil.exceedsLength(updatedProfile.getHeadline(), 200)) {
            throw new ValidationException("Headline is too long.");
        }
        updatedProfile.setUserId(userId);
        userProfileDAO.update(updatedProfile);
    }

    @Override
    public void changePassword(int userId, String currentPlainPassword, String newPlainPassword) throws SQLException {
        User user = getUserById(userId);
        if (!PasswordUtil.matches(currentPlainPassword, user.getPasswordHash())) {
            throw new AuthenticationException("Current password is incorrect.");
        }
        if (!PasswordUtil.isStrongEnough(newPlainPassword)) {
            throw new ValidationException("New password must be at least 8 characters and contain both letters and numbers.");
        }
        userDAO.updatePasswordHash(userId, PasswordUtil.hash(newPlainPassword));
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDAO.findAll();
    }

    @Override
    public void setUserActive(int userId, boolean active) throws SQLException {
        userDAO.setActive(userId, active);
    }

    @Override
    public int countAllUsers() throws SQLException {
        return userDAO.countAll();
    }

    @Override
    public int countPortfolioUsers() throws SQLException {
        return userDAO.countByRoleName("PORTFOLIO_USER");
    }
}