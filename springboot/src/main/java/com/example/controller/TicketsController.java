package com.example.controller;

import com.example.common.Result;
import com.example.entity.Tickets;
import com.example.service.TicketsService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ticket")
public class TicketsController {

    @Resource
    private TicketsService ticketsService; // 假设你的工单服务名为TicketService

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10")Integer pageSize,
                             Tickets ticket){
        PageInfo<Tickets> ticketsPageInfo = ticketsService.selectPage(pageNum, pageSize, ticket);
        return Result.success(ticketsPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Tickets ticket){
        ticketsService.add(ticket);
        return Result.success();
    }


    @PutMapping("/update")
    public Result update(@RequestBody Tickets ticket){
        ticketsService.updateById(ticket);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        ticketsService.deleteById(id);
        return Result.success();
    }




}