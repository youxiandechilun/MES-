package com.example.service;

import com.example.common.Result;
import com.example.entity.Employees;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service

public class UserService {

    @Resource
    private UserMapper userMapper;

    public Result login(User user) {
        // 查询数据库中的用户信息
        User dbUser = userMapper.findByUsername(user.getUsername());

        if (dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
            // 检查角色是否一致
            if (dbUser.getRole().equals(user.getRole())) {
                // 角色一致，登录成功
                return Result.success(dbUser);
            } else {
                // 角色不一致，返回错误信息
                return Result.error("角色不匹配或账号密码错误");
            }
        } else {
            // 用户名或密码错误
            return Result.error("角色不匹配或账号密码错误");
        }
    }

    public Employees selectByUsername(String username) {

        Employees employees = userMapper.findByUsername2(username);

        return employees;

    }

}



