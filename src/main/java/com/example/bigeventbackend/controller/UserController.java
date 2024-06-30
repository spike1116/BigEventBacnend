package com.example.bigeventbackend.controller;

import com.example.bigeventbackend.pojo.Result;
import com.example.bigeventbackend.pojo.User;
import com.example.bigeventbackend.service.UserService;
import com.example.bigeventbackend.utils.JwtUtils;
import com.example.bigeventbackend.utils.Md5Util;
import com.example.bigeventbackend.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByUserName(username);
        if (loginUser.getUsername() == null) {
            return Result.error("用户名错误");
        }
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtils.genToken(claims);//登录时生成Token
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo(@RequestHeader(name = "Authorization") String token) {
//      Map<String, Object> userToken = JwtUtils.parseToken(token); //通过解析Jwt Token来获取用户名
        Map<String, Object> userToken = ThreadLocalUtil.get(); //通过ThreadLocal获取Token

        String username = (String) userToken.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        System.out.println(user.getUpdateTime());
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String path) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userService.updateAvatar(path, id);
        return Result.success();
    }

    @PatchMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> params) {
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String reNewPwd = params.get("reNewPwd");

        Map<String, Object> userToken = ThreadLocalUtil.get(); //通过ThreadLocal获取Token
        String username = (String) userToken.get("username");
        Integer id = (Integer) userToken.get("id");

        String md5Password = userService.findByUserName(username).getPassword();
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(reNewPwd)) {
            return Result.error("缺少必要参数");
        }
        if (!Md5Util.checkPassword(oldPwd, md5Password)) {
            return Result.error("旧密码不正确");
        } else if (!reNewPwd.equals(newPwd)) {
            return Result.error("两次输入的密码不一致");
        }
        userService.updatePassword(id, newPwd);
        return Result.success();
    }
}
