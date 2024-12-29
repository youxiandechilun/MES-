package com.example.controller;

import com.example.common.Result;
import com.example.entity.Contract;
import com.example.entity.Customer;
import com.example.entity.DeviceRecord;
import com.example.mapper.DeviceRecordMapper;
import com.example.service.CustomerService;
import com.example.service.DeviceRecordService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/deviceRecord")
public class DeviceRecordController {

    @Resource
    private DeviceRecordService deviceRecordService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             DeviceRecord deviceRecord){
        PageInfo<DeviceRecord> deviceRecordPageInfo = deviceRecordService.selectPage(pageNum, pageSize,deviceRecord);
        return Result.success(deviceRecordPageInfo);
    }

//    @GetMapping("/insert1")
//    public Result insert1(
//            @RequestParam String deviceId,
//            @RequestParam String deviceName,
//            @RequestParam String worker,
//            @RequestParam String processName,
//            @RequestParam String useDate,
//            @RequestParam String startTime,
//            @RequestParam Double depreciationRate,
//            @RequestParam Double electricityRate // 电费率（元/小时）
//    ) {
//        // 在这里添加你的业务逻辑
//        // 例如：保存数据到数据库、调用服务等
//
//        // 假设Result是一个已定义好的类，用于封装返回给客户端的数据
//        return new Result("操作成功", "具体信息");
//    }

    @PostMapping("/add1")
    public Result add1(@RequestBody DeviceRecord deviceRecord){
        deviceRecordService.add1(deviceRecord);
        return Result.success(deviceRecord);
    }

    @PostMapping("/add2")
    public Result add2(@RequestBody DeviceRecord deviceRecord){
        deviceRecordService.add2(deviceRecord);
        return Result.success(deviceRecord);
    }

    // 根据设备编号 ，判断设备开始时间存在，并且设备结束时间不存在
   @GetMapping("/select1")
    public Result select1(@RequestParam String deviceId){

        return Result.success(deviceRecordService.select1(deviceId));
   }

    @GetMapping("/select2")
    public Result select2(@RequestParam String deviceId){

        return Result.success(deviceRecordService.select2(deviceId));
    }


}
