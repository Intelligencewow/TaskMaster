package com.example.taskmaster.model;

import java.sql.Date;


public class Task {
    private String title;
    private String description;
    private java.sql.Date creationDate;
    private java.sql.Date conclusionDate;
    private String status;
    private Long categoryId;
    private Long userId;
    private boolean active;

    public Task(String title, String description, java.sql.Date creationDate, java.sql.Date conclusionDate, String status, Long categoryId, Long userId, boolean active) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.conclusionDate = conclusionDate;
        this.status = status;
        this.categoryId = categoryId;
        this.userId = userId;
        this.active = active;
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

    public java.sql.Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.sql.Date creationDate) {
        this.creationDate = creationDate;
    }

    public java.sql.Date getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(Date conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}