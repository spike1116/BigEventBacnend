package com.example.bigeventbackend.service;

import com.example.bigeventbackend.pojo.Article;
import com.example.bigeventbackend.pojo.PageBean;

public interface ArticleService {

    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum,Integer pageSize,Integer categoryId,String state);

    void update(Article article);

    Article findById(Integer id);

    void deleteById(Integer id);
}
