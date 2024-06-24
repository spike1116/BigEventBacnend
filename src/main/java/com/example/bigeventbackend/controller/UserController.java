package com.example.bigeventbackend.controller;

import com.example.bigeventbackend.pojo.Result;
import com.example.bigeventbackend.pojo.User;
import com.example.bigeventbackend.service.UserService;
import com.example.bigeventbackend.service.impl.UserServiceImpl;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result<User> register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        if (userService.findByUserName(username) == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用～");
        }
    }
}
