package com.example.bigeventbackend.service.impl;

import com.example.bigeventbackend.mapper.ArticleMapper;
import com.example.bigeventbackend.pojo.Article;
import com.example.bigeventbackend.pojo.PageBean;
import com.example.bigeventbackend.service.ArticleService;
import com.example.bigeventbackend.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //1.创建PageBean对象
        PageBean<Article> articlePageBean = new PageBean<>();
        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);//pageNum 当前页 pageSize 分页参数

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");

        List<Article> articleList = articleMapper.list(userId, categoryId, state);

        //Page中提供了方法，可以获取PageHelper分页查询后，的到的总记录条数和当前页数据
        Page<Article> page = (Page<Article>) articleList;

        articlePageBean.setTotal(page.getTotal());
        articlePageBean.setItems(page.getResult());

        return articlePageBean;
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public Article findById(Integer id) {
        return articleMapper.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }
}
