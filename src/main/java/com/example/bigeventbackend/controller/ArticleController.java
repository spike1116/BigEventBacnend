package com.example.bigeventbackend.controller;


import com.example.bigeventbackend.pojo.Article;
import com.example.bigeventbackend.pojo.PageBean;
import com.example.bigeventbackend.pojo.Result;
import com.example.bigeventbackend.service.ArticleService;
import com.example.bigeventbackend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated(Article.Add.class) Article article) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer createUserId = (Integer) claims.get("id");
        article.setCreateUser(createUserId);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> articlePageBean = articleService.list(pageNum,pageSize,categoryId,state);
        return  Result.success(articlePageBean);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article){
        articleService.update(article);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<Article> findById(@RequestParam Integer id){
        Article article = articleService.findById(id);
        return Result.success(article);
    }

    @DeleteMapping
    public Result deleteById(@RequestParam Integer id){
        if(findById(id)!=null){
            articleService.deleteById(id);
            return Result.success();
        }
        return Result.error("文章不存在");

    }
}
