package com.example.mapper;

import com.example.entity.Employees;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "role", column = "role")
    })
    User findByUsername(String username);

    @Select("SELECT * FROM employees WHERE username = #{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "role", column = "role"),
            @Result(property = "workshop", column = "workshop"),
            @Result(property = "teamsGroups",column = "teamsGroups"),
    })
    Employees findByUsername2(String username);



}
