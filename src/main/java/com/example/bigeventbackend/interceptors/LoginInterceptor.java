package com.example.bigeventbackend.interceptors;

import com.example.bigeventbackend.pojo.Result;
import com.example.bigeventbackend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //统一进行令牌验证操作
        String token = request.getHeader("Authorization");//获取到请求头中的token
        try {
            Map<String, Object> claims = JwtUtils.parseToken(token);
            return true;//token验证通过，返回true
        } catch (Exception e) {
            response.setStatus(401);
            return false;//token验证失败
        }
    }
}
