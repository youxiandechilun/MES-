package com.example.service;

import com.example.common.Result;
import com.example.entity.Employees;
import com.example.entity.Order;
import com.example.mapper.EmployeesMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesMapper employeesMapper;

    public Result registerEmployee(
            String no, String username, String password,
            String name, String workshop, String teamsGroups, String sex,
            @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8") Date seniority,
            int grade, String avatar, String history, String role
    ) {
        // 调用mapper层的方法进行员工注册
        int employeeResult = employeesMapper.registerEmployee(
                no, username, password, name, workshop, teamsGroups, sex, seniority, grade, avatar, history, role);

        // 调用mapper层的方法进行用户注册
        int userResult = employeesMapper.registerUser(username, password, name, role);

        // 确保两个操作都成功
        if (employeeResult > 0 && userResult > 0) {
            return Result.success("注册成功");
        } else {
            return Result.error("账号重复，请重新尝试");
        }
    }

    public List<Map<String, Object>>  selectname(String workshop, String teamsGroups) {
        // 调用Mapper接口的方法，传入Employees对象
        return employeesMapper.selectname(workshop, teamsGroups);
    }



    public PageInfo<Employees> selectPage(Integer pageNum, Integer pageSize, Employees employee) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employees> employeeList = employeesMapper.selectAll(employee);
        return new PageInfo<>(employeeList);  // 使用构造函数创建PageInfo对象
    }



    @Transactional
    public void updateById(Employees employee) {

        employeesMapper.updateById(employee);
    }


    @Transactional
    public void deleteById(Integer id) {

        employeesMapper.deleteById(id);
    }





}