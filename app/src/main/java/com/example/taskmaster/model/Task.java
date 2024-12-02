package com.example.taskmaster.model;

import java.sql.Date;
import java.time.LocalDate;


public class Task {
    private String title;
    private String description;
    private String creationDate;
    private String conclusionDate;
    private String status;
    private Long categoryId;
    private Long userId;
    private boolean active;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.creationDate = LocalDate.now().toString();
        this.conclusionDate = "2040-04-20";
        this.status = "pending";
        this.categoryId = 1L;
        this.userId = 1L;
        this.active = true;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(String conclusionDate) {
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