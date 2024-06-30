package com.example.bigeventbackend.service.impl;

import com.example.bigeventbackend.mapper.UserMapper;
import com.example.bigeventbackend.pojo.User;
import com.example.bigeventbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bigeventbackend.utils.*;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        //加密密码
        String md5String = Md5Util.getMD5String(password);
        //Test
        System.out.println(md5String);

        //添加用户
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());

        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String path,Integer id) {
        userMapper.updateAvatar(path,id);
    }

    @Override
    public void updatePassword(Integer id,String newPwd) {
        userMapper.updatePassword(id,Md5Util.getMD5String(newPwd));
    }
}
