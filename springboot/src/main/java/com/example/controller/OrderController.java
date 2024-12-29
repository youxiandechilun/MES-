package com.example.controller;

import com.example.common.Result;
import com.example.entity.Order;
import com.example.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Order order) {
        PageInfo<Order> orderPageInfo = orderService.selectPage(pageNum, pageSize, order);
        return Result.success(orderPageInfo);
    }

    @GetMapping("/selectPage2")
    public Result selectPage2(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Order order) {
        PageInfo<Order> orderPageInfo = orderService.selectPage2(pageNum, pageSize, order);
        return Result.success(orderPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Order order) {
        // 移除多余的分号
        orderService.add(order);
        return Result.success(order);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Order order) {  // 修改为接收Order对象
        orderService.updateById(order);  // 确保这里的方法名正确
        return Result.success();
    }

    @PutMapping("/update2")
    public Result update2(@RequestBody Order order) {  // 修改为接收Order对象
        orderService.updateById2(order);  // 确保这里的方法名正确
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        orderService.deleteById(id);  // 使用orderService进行删除操作
        return Result.success();
    }
}