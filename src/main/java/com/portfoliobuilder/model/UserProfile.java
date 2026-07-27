package com.portfoliobuilder.model;

import java.time.LocalDateTime;

public class UserProfile {

    private int profileId;
    private int userId;
    private String fullName;
    private String headline;
    private String profileImageUrl;
    private String aboutText;
    private String phone;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserProfile() {
    }

    public UserProfile(int profileId, int userId, String fullName, String headline, String profileImageUrl,
                        String aboutText, String phone, String location,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.profileId = profileId;
        this.userId = userId;
        this.fullName = fullName;
        this.headline = headline;
        this.profileImageUrl = profileImageUrl;
        this.aboutText = aboutText;
        this.phone = phone;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getAboutText() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        return "UserProfile{profileId=" + profileId + ", userId=" + userId + ", fullName=" + fullName + "}";
    }
}