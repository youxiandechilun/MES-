package com.example.service;

import com.example.entity.Contract;
import com.example.entity.Customer;
import com.example.mapper.ContractMapper;
import com.example.mapper.CustomerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    public PageInfo<Customer> selectPage(Integer pageNum, Integer pageSize, Customer customer){
        PageHelper.startPage(pageNum,pageSize);
        List<Customer> customerList = customerMapper.selectAll(customer);
        return PageInfo.of(customerList);
    }
}
