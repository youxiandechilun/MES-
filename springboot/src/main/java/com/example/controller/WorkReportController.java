package com.example.controller;

import com.example.common.Result;
import com.example.entity.Contract;
import com.example.entity.WorkReport;
import com.example.service.ContractService;
import com.example.service.WorkReportService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/workReport")
public class WorkReportController {

    @Resource
    private WorkReportService workReportService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             WorkReport workReport){
        PageInfo<WorkReport> workReportPageInfo = workReportService.selectPage(pageNum, pageSize,workReport);
        return Result.success(workReportPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody WorkReport workReport){
        workReportService.add( workReport);
        return Result.success( workReport);
    }
}
