package com.example.mapper;

import com.example.entity.DeviceInfo;
import com.example.entity.DeviceRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeviceInfoMapper {

    @Select("SELECT * FROM deviceinfo WHERE deviceId LIKE CONCAT('%', #{deviceId}, '%') AND deviceName LIKE CONCAT('%', #{deviceName}, '%') ORDER BY id ASC")
    List<DeviceInfo> selectAll(DeviceInfo deviceInfo);
}
