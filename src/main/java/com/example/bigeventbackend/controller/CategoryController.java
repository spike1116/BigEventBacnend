package com.example.bigeventbackend.controller;

import com.example.bigeventbackend.pojo.Category;
import com.example.bigeventbackend.pojo.Result;
import com.example.bigeventbackend.service.CategoryService;
import com.example.bigeventbackend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //新增文章分类
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){

        Map<String,Object> claims=  ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        category.setCreateUser(userId);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryService.addCategory(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list(){
        List<Category> categoryList = categoryService.getAllCategory();
        return Result.success(categoryList);
    }

    @GetMapping("/detail")
    public Result<Category> findById(@RequestParam Integer id){
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteById(@RequestParam Integer id){
        if(categoryService.findById(id)!=null){
            categoryService.deleteById(id);
            return Result.success();
        }
        return Result.error("该分类不存在");
    }
}
