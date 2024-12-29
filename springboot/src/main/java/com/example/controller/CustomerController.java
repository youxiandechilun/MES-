package com.example.controller;
import com.example.common.Result;
import com.example.entity.Customer;
import com.example.service.CustomerService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Customer customer){
        PageInfo<Customer> customerPageInfo = customerService.selectPage(pageNum, pageSize,customer);
        return Result.success(customerPageInfo);
    }


}
