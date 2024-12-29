package com.example.service;
import com.example.entity.Order;
import com.example.mapper.OrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    public PageInfo<Order> selectPage(Integer pageNum, Integer pageSize, Order order) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList =  orderMapper.selectAll(order);
        return new PageInfo<>(orderList);  // 使用构造函数创建PageInfo对象
    }

    public PageInfo<Order> selectPage2(Integer pageNum, Integer pageSize, Order order) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList =  orderMapper.selectAll2(order);
        return new PageInfo<>(orderList);  // 使用构造函数创建PageInfo对象
    }

    @Transactional
    public void add(Order order) {

        orderMapper.insert(order);
    }

    @Transactional
    public void updateById(Order order) {

        orderMapper.updateById(order);
    }

    @Transactional
    public void updateById2(Order order) {

        orderMapper.updateById2(order);
    }

    @Transactional
    public void deleteById(Integer id) {

        orderMapper.deleteById(id);
    }
}