package com.example.controller;
import com.example.common.Result;
import com.example.entity.Employees;
import com.example.entity.Order;
import com.example.service.EmployeesService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @PostMapping("/register")
    public Result register(@RequestBody Employees employee) {
        // 调用服务层的方法进行注册
        return employeesService.registerEmployee( employee.getNo(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getName(),
                employee.getWorkshop(),
                employee.getTeamsGroups(),
                employee.getSex(),
                employee.getSeniority(),
                employee.getGrade(),
                employee.getAvatar(),
                employee.getHistory(),
                employee.getRole());
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestBody Employees employee) {
        PageInfo<Employees> orderPageInfo = employeesService.selectPage(pageNum, pageSize, employee);
        return Result.success(orderPageInfo);
    }

    @GetMapping("/selectname")
    public List<Map<String, Object>>  selectname(@RequestParam String workshop, @RequestParam String teamsGroups) {
        System.out.println("Workshop: " + workshop);
        System.out.println("TeamsGroups: " + teamsGroups);
        return employeesService.selectname(workshop, teamsGroups);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Employees employee) {
        employeesService.updateById(employee);  // 确保这里的方法名正确
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        employeesService.deleteById(id);  // 使用orderService进行删除操作
        return Result.success();
    }


}
