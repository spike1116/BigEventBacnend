package com.example.bigeventbackend.annotation;

import com.example.bigeventbackend.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StateValidation.class)
public @interface State {
    //提供校验失败后的提示信息
    String message() default "文章状态只能是:已发布或草稿"; //自定义校验注解进行参数校验

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
