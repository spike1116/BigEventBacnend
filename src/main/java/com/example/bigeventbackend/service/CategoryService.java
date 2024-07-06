package com.example.bigeventbackend.service;

import com.example.bigeventbackend.pojo.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> getAllCategory();

    Category findById(Integer id);

    void update(Category category);

    void deleteById(Integer id);
}
