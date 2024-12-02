package com.example.taskmaster.model;

import java.util.Set;

public class Category {
    private Long id;
    private String categoryName;
    private Set<Task> tasks;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}