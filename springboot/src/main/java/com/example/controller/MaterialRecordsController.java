package com.example.controller;

import com.example.common.Result;
import com.example.entity.Contract;
import com.example.entity.MaterialRecords;
import com.example.service.ContractService;
import com.example.service.MaterialRecordService;
import com.example.service.MaterialService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/materialrecords")
public class MaterialRecordsController {

    @Resource
    private MaterialRecordService materialRecordService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             MaterialRecords materialRecords){
        PageInfo<MaterialRecords> MaterialRecordsPageInfo = materialRecordService.selectPage(pageNum, pageSize,materialRecords);
        return Result.success(MaterialRecordsPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody MaterialRecords materialRecords){
        materialRecordService.add(materialRecords);
        return Result.success(materialRecords);
    }
}
