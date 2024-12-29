package com.example.entity;

import lombok.Data;

@Data
public class DeviceInfo {

    private Integer id; // 主键ID
    private String deviceId; // 设备编号
    private String deviceName; // 设备名称
    private Double depreciationRate; // 折旧率（百分比）
    private Double electricityRate; // 电费率（元/小时）

}