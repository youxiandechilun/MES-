package com.example.mapper;

import com.example.entity.Contract;
import com.example.entity.Customer;
import com.example.entity.DeviceRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceRecordMapper {


    @Select("SELECT * FROM devicerecords WHERE deviceId LIKE CONCAT('%', #{deviceId}, '%') AND worker LIKE CONCAT('%', #{worker}, '%') ORDER BY id DESC")
    List<DeviceRecord> selectAll(DeviceRecord deviceRecord);

    @Insert("INSERT INTO devicerecords (deviceId, deviceName, worker, processName, useDate, startTime, depreciationRate, electricityRate) " +
            "VALUES (#{deviceId}, #{deviceName}, #{worker}, #{processName}, #{useDate}, " +
            "#{startTime}, #{depreciationRate}, #{electricityRate})")
    void insert1( DeviceRecord deviceRecord);

    @Update("UPDATE devicerecords SET endTime = #{endTime} WHERE deviceId = #{deviceId}")
    void update1(DeviceRecord deviceRecord);

    @Select("SELECT COUNT(*) FROM devicerecords WHERE deviceId = #{deviceId} AND startTime IS NOT NULL AND endTime IS NULL")
    int checkStartExists( String deviceId);

    @Select("SELECT COUNT(*) FROM devicerecords WHERE deviceId = #{deviceId} AND startTime IS NOT NULL AND endTime IS NOT NULL")
    int checkBothExists( String deviceId);

}
