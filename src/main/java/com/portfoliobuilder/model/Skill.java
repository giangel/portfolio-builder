package com.portfoliobuilder.model;

import java.time.LocalDateTime;

public class Skill {

    private int skillId;
    private int portfolioId;
    private String skillName;
    private ProficiencyLevel proficiencyLevel;
    private int displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Skill() {
    }

    public Skill(int skillId, int portfolioId, String skillName, ProficiencyLevel proficiencyLevel,
                 int displayOrder, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.skillId = skillId;
        this.portfolioId = portfolioId;
        this.skillName = skillName;
        this.proficiencyLevel = proficiencyLevel;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
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
        return "Skill{skillId=" + skillId + ", skillName=" + skillName + ", proficiencyLevel=" + proficiencyLevel + "}";
    }
}