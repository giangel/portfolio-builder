package com.portfoliobuilder.dao;

import com.portfoliobuilder.model.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDAO {

    void create(int userId, String message) throws SQLException;

    List<Notification> findByUserId(int userId) throws SQLException;

    void markRead(int notificationId) throws SQLException;
}