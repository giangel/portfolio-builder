package com.portfoliobuilder.service.impl;

import com.portfoliobuilder.dao.UserDAO;
import com.portfoliobuilder.dao.UserProfileDAO;
import com.portfoliobuilder.dao.impl.UserDAOImpl;
import com.portfoliobuilder.dao.impl.UserProfileDAOImpl;
import com.portfoliobuilder.exception.AuthenticationException;
import com.portfoliobuilder.exception.ValidationException;
import com.portfoliobuilder.model.RoleName;
import com.portfoliobuilder.model.User;
import com.portfoliobuilder.model.UserProfile;
import com.portfoliobuilder.service.AuthenticationService;
import com.portfoliobuilder.util.PasswordUtil;
import com.portfoliobuilder.util.ValidationUtil;

import java.sql.SQLException;
import java.util.Optional;

public class AuthenticationServiceImpl implements AuthenticationService {

    private static final int PORTFOLIO_USER_ROLE_ID = 2;

    private final UserDAO userDAO = new UserDAOImpl();
    private final UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
    private final com.portfoliobuilder.dao.AuditLogDAO auditLogDAO = new com.portfoliobuilder.dao.impl.AuditLogDAOImpl();

    @Override
    public User register(String email, String plainPassword, String fullName) throws SQLException {
        if (!ValidationUtil.isValidEmail(email)) {
            throw new ValidationException("Please enter a valid email address.");
        }
        if (!PasswordUtil.isStrongEnough(plainPassword)) {
            throw new ValidationException("Password must be at least 8 characters and contain both letters and numbers.");
        }
        if (ValidationUtil.isBlank(fullName)) {
            throw new ValidationException("Full name is required.");
        }
        if (userDAO.existsByEmail(email)) {
            throw new ValidationException("An account with this email already exists.");
        }

        User user = new User();
        user.setRoleId(PORTFOLIO_USER_ROLE_ID);
        user.setEmail(email.trim().toLowerCase());
        user.setPasswordHash(PasswordUtil.hash(plainPassword));
        user.setActive(true);
        User createdUser = userDAO.create(user);

        UserProfile profile = new UserProfile();
        profile.setUserId(createdUser.getUserId());
        profile.setFullName(fullName.trim());
        userProfileDAO.create(profile);

        createdUser.setRoleName(RoleName.PORTFOLIO_USER);
        auditLogDAO.log(createdUser.getUserId(), "REGISTER", "email=" + createdUser.getEmail());
        return createdUser;
    }

    @Override
    public User login(String email, String plainPassword) throws SQLException {
        if (ValidationUtil.isBlank(email) || ValidationUtil.isBlank(plainPassword)) {
            throw new AuthenticationException("Email and password are required.");
        }
        Optional<User> userOptional = userDAO.findByEmail(email.trim().toLowerCase());
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Invalid email or password.");
        }
        User user = userOptional.get();
        if (!user.isActive()) {
            throw new AuthenticationException("This account has been deactivated. Contact an administrator.");
        }
        if (!PasswordUtil.matches(plainPassword, user.getPasswordHash())) {
            throw new AuthenticationException("Invalid email or password.");
        }
        auditLogDAO.log(user.getUserId(), "LOGIN", "email=" + user.getEmail());
        return user;
    }
}