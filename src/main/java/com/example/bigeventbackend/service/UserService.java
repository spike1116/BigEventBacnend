package com.example.bigeventbackend.service;

import com.example.bigeventbackend.pojo.User;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface UserService {

    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String path,Integer id);

    void updatePassword(Integer id, String newPwd);
}
