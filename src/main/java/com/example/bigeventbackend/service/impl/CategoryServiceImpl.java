package com.example.bigeventbackend.service.impl;


import com.example.bigeventbackend.mapper.CategoryMapper;
import com.example.bigeventbackend.mapper.UserMapper;
import com.example.bigeventbackend.pojo.Category;
import com.example.bigeventbackend.service.CategoryService;
import com.example.bigeventbackend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(Category category) {
        categoryMapper.addCategory(category);
    }

    @Override
    public List<Category> getAllCategory() {
        Map<String,Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        return categoryMapper.getAllCategory(id);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }
}
