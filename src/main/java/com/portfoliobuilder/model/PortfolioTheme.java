package com.portfoliobuilder.model;

import java.time.LocalDateTime;

public class PortfolioTheme {

    private int themeId;
    private String themeName;
    private String themeKey;
    private String description;
    private String layoutFamily;
    private boolean active;
    private LocalDateTime createdAt;

    public PortfolioTheme() {
    }

    public PortfolioTheme(int themeId, String themeName, String themeKey, String description,
                           String layoutFamily, boolean active, LocalDateTime createdAt) {
        this.themeId = themeId;
        this.themeName = themeName;
        this.themeKey = themeKey;
        this.description = description;
        this.layoutFamily = layoutFamily;
        this.active = active;
        this.createdAt = createdAt;
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

    public String getThemeKey() {
        return themeKey;
    }

    public void setThemeKey(String themeKey) {
        this.themeKey = themeKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLayoutFamily() {
        return layoutFamily;
    }

    public void setLayoutFamily(String layoutFamily) {
        this.layoutFamily = layoutFamily;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PortfolioTheme{themeId=" + themeId + ", themeName=" + themeName + "}";
    }
}