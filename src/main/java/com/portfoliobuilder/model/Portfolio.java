package com.portfoliobuilder.model;

import java.time.LocalDateTime;

public class Portfolio {

    private int portfolioId;
    private int userId;
    private int themeId;
    private String themeName;
    private String title;
    private String slug;
    private String description;
    private boolean published;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Portfolio() {
    }

    public Portfolio(int portfolioId, int userId, int themeId, String themeName, String title, String slug,
                      String description, boolean published, LocalDateTime publishedAt,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.themeId = themeId;
        this.themeName = themeName;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.published = published;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Portfolio{portfolioId=" + portfolioId + ", title=" + title + ", slug=" + slug
                + ", published=" + published + "}";
    }
}