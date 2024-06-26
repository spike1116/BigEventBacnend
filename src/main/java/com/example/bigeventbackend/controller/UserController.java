package com.example.bigeventbackend.controller;

import com.example.bigeventbackend.pojo.Result;
import com.example.bigeventbackend.pojo.User;
import com.example.bigeventbackend.service.UserService;
import com.example.bigeventbackend.service.impl.UserServiceImpl;
import com.example.bigeventbackend.utils.JwtUtils;
import com.example.bigeventbackend.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        User loginUser = userService.findByUserName(username);
        if(loginUser.getUsername()==null){
            return Result.error("用户名错误");
        }
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtils.genToken(claims);//登录时生成Token
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo(@RequestHeader(name = "Authorization") String token){
        Map<String, Object> userToken = JwtUtils.parseToken(token); //通过解析Jwt Token来获取用户名
        String username = (String)userToken.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }
}
