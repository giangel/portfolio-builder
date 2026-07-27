package com.portfoliobuilder.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Project {

    private int projectId;
    private int portfolioId;
    private String title;
    private String description;
    private String imageUrl;
    private String projectUrl;
    private String repositoryUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private int displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Project() {
    }

    public Project(int projectId, int portfolioId, String title, String description, String imageUrl,
                    String projectUrl, String repositoryUrl, LocalDate startDate, LocalDate endDate,
                    int displayOrder, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.projectId = projectId;
        this.portfolioId = portfolioId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.projectUrl = projectUrl;
        this.repositoryUrl = repositoryUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
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
        return "Project{projectId=" + projectId + ", title=" + title + "}";
    }
}