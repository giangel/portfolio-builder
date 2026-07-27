package com.portfoliobuilder.model;

import java.time.LocalDateTime;

public class SocialLink {

    private int socialLinkId;
    private int portfolioId;
    private SocialPlatform platform;
    private String url;
    private int displayOrder;
    private LocalDateTime createdAt;

    public SocialLink() {
    }

    public SocialLink(int socialLinkId, int portfolioId, SocialPlatform platform, String url,
                       int displayOrder, LocalDateTime createdAt) {
        this.socialLinkId = socialLinkId;
        this.portfolioId = portfolioId;
        this.platform = platform;
        this.url = url;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
    }

    public int getSocialLinkId() {
        return socialLinkId;
    }

    public void setSocialLinkId(int socialLinkId) {
        this.socialLinkId = socialLinkId;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public SocialPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(SocialPlatform platform) {
        this.platform = platform;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public String toString() {
        return "SocialLink{socialLinkId=" + socialLinkId + ", platform=" + platform + ", url=" + url + "}";
    }
}