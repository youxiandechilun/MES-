package com.example.service;

import com.example.entity.DeviceInfo;
import com.example.entity.DeviceRecord;
import com.example.mapper.DeviceInfoMapper;
import com.example.mapper.DeviceRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeviceInfoService {
    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    public PageInfo<DeviceInfo> selectPage(Integer pageNum, Integer pageSize, DeviceInfo deviceInfo){
        PageHelper.startPage(pageNum,pageSize);
        List<DeviceInfo> deviceInfoList = deviceInfoMapper.selectAll(deviceInfo);
        return PageInfo.of(deviceInfoList);
    }

}
