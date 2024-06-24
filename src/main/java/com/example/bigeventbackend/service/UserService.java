package com.example.bigeventbackend.service;

import com.example.bigeventbackend.pojo.User;
import org.springframework.stereotype.Service;


public interface UserService {

    User findByUserName(String username);

    void register(String username, String password);
}
