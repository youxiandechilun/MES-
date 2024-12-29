package com.example.service;

import com.example.entity.Tickets;
import com.example.mapper.TicketsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TicketsService {

    @Resource
    private TicketsMapper ticketsMapper;

    public PageInfo<Tickets> selectPage(Integer pageNum, Integer pageSize, Tickets tickets){
        PageHelper.startPage(pageNum,pageSize);
        List<Tickets> ticketsList = ticketsMapper.selectAll(tickets);
        return PageInfo.of(ticketsList);
    }

    public void add(Tickets tickets) {
        ticketsMapper.insert(tickets);
    }

    public void updateById(Tickets tickets) {
        ticketsMapper.updateById(tickets);
    }

    public void deleteById(Integer id) {
        ticketsMapper.deleteById(id);
    }

}
