package com.example.bigeventbackend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;//主键ID

    @NotEmpty
    @NotBlank
    private String categoryName;//分类名称

    @NotBlank
    @NotEmpty
    private String categoryAlias;//分类别名

    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //指定日期时间格式
    private LocalDateTime updateTime;//更新时间


    //如果某个校验项没有指定分组，默认属于Default分组
    //分组之间可以继承，A extends B，那么A中拥有B中的所有校验项

    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
