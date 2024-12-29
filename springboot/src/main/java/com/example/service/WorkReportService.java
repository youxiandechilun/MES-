package com.example.service;

import com.example.entity.Contract;
import com.example.entity.WorkReport;
import com.example.mapper.ContractMapper;
import com.example.mapper.WorkReportMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorkReportService {

    @Resource
    private WorkReportMapper workReportMapper;


    public PageInfo<WorkReport> selectPage(Integer pageNum, Integer pageSize, WorkReport workReport){
        PageHelper.startPage(pageNum,pageSize);
        List<WorkReport> workReportList = workReportMapper.selectAll(workReport);
        return PageInfo.of(workReportList);
    }


    @Transactional
    public void add(WorkReport workReport) {

        workReportMapper.insert(workReport);

    }


}
