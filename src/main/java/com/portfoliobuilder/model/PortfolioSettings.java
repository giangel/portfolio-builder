package com.portfoliobuilder.model;

import java.time.LocalDateTime;

public class PortfolioSettings {

    private int settingId;
    private int portfolioId;
    private String accentColor;
    private String typographyChoice;
    private String backgroundStyle;
    private String buttonStyle;
    private String layoutVariant;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PortfolioSettings() {
    }

    public PortfolioSettings(int settingId, int portfolioId, String accentColor, String typographyChoice,
                              String backgroundStyle, String buttonStyle, String layoutVariant,
                              LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.settingId = settingId;
        this.portfolioId = portfolioId;
        this.accentColor = accentColor;
        this.typographyChoice = typographyChoice;
        this.backgroundStyle = backgroundStyle;
        this.buttonStyle = buttonStyle;
        this.layoutVariant = layoutVariant;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getSettingId() {
        return settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    public String getTypographyChoice() {
        return typographyChoice;
    }

    public void setTypographyChoice(String typographyChoice) {
        this.typographyChoice = typographyChoice;
    }

    public String getBackgroundStyle() {
        return backgroundStyle;
    }

    public void setBackgroundStyle(String backgroundStyle) {
        this.backgroundStyle = backgroundStyle;
    }

    public String getButtonStyle() {
        return buttonStyle;
    }

    public void setButtonStyle(String buttonStyle) {
        this.buttonStyle = buttonStyle;
    }

    public String getLayoutVariant() {
        return layoutVariant;
    }

    public void setLayoutVariant(String layoutVariant) {
        this.layoutVariant = layoutVariant;
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
        return "PortfolioSettings{portfolioId=" + portfolioId + ", accentColor=" + accentColor + "}";
    }
}