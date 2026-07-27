package com.portfoliobuilder.model;

import java.time.LocalDateTime;

public class PortfolioSection {

    private int sectionId;
    private int portfolioId;
    private SectionType sectionType;
    private int displayOrder;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PortfolioSection() {
    }

    public PortfolioSection(int sectionId, int portfolioId, SectionType sectionType, int displayOrder,
                             boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.sectionId = sectionId;
        this.portfolioId = portfolioId;
        this.sectionType = sectionType;
        this.displayOrder = displayOrder;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
        return "PortfolioSection{sectionId=" + sectionId + ", sectionType=" + sectionType
                + ", displayOrder=" + displayOrder + ", enabled=" + enabled + "}";
    }
}