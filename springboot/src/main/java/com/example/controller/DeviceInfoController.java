package com.example.controller;

import com.example.common.Result;
import com.example.entity.DeviceInfo;
import com.example.entity.DeviceRecord;
import com.example.service.DeviceInfoService;
import com.example.service.DeviceRecordService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/deviceInfo")
public class DeviceInfoController {

    @Resource
    private DeviceInfoService deviceInfoService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             DeviceInfo deviceInfo){
        PageInfo<DeviceInfo> deviceInfoPageInfo = deviceInfoService.selectPage(pageNum, pageSize,deviceInfo);
        return Result.success(deviceInfoPageInfo);
    }
}
