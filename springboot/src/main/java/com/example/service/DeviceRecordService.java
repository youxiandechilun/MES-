package com.example.service;

import com.example.entity.Contract;
import com.example.entity.Customer;
import com.example.entity.DeviceRecord;
import com.example.mapper.CustomerMapper;
import com.example.mapper.DeviceRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeviceRecordService {

    @Resource
    private DeviceRecordMapper deviceRecordMapper;

    public PageInfo<DeviceRecord> selectPage(Integer pageNum, Integer pageSize, DeviceRecord deviceRecord){
        PageHelper.startPage(pageNum,pageSize);
        List<DeviceRecord> deviceRecordList = deviceRecordMapper.selectAll(deviceRecord);
        return PageInfo.of(deviceRecordList);
    }

    @Transactional
    public void add1(DeviceRecord deviceRecord) {

        deviceRecordMapper.insert1(deviceRecord);

    }

    @Transactional
    public void add2(DeviceRecord deviceRecord) {

        deviceRecordMapper.update1(deviceRecord);

    }

    public int select1(String deviceId) {

        return deviceRecordMapper.checkStartExists(deviceId);
    }

    public int select2(String deviceId) {

        return deviceRecordMapper.checkBothExists(deviceId);
    }

}
