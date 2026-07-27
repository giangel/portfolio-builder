package com.portfoliobuilder.service;

import com.portfoliobuilder.model.User;

import java.sql.SQLException;

public interface AuthenticationService {

    User register(String email, String plainPassword, String fullName) throws SQLException;

    User login(String email, String plainPassword) throws SQLException;
}