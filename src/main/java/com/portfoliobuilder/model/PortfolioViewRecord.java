package com.portfoliobuilder.model;

import java.time.LocalDateTime;

public class PortfolioViewRecord {

    private int viewId;
    private int portfolioId;
    private LocalDateTime viewedAt;
    private String ipAddress;
    private String userAgent;

    public PortfolioViewRecord() {
    }

    public PortfolioViewRecord(int viewId, int portfolioId, LocalDateTime viewedAt,
                                String ipAddress, String userAgent) {
        this.viewId = viewId;
        this.portfolioId = portfolioId;
        this.viewedAt = viewedAt;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "PortfolioViewRecord{viewId=" + viewId + ", portfolioId=" + portfolioId + ", viewedAt=" + viewedAt + "}";
    }
}