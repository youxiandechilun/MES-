package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class DeviceRecord {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 操作工
     */
    private String worker;

    /**
     * 工序名称
     */
    private String processName;

    /**
     * 使用日期
     */
    private String useDate;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 运行时间（单位：小时）
     */
    private BigDecimal runtime;


    /**
     * 折旧费用
     */
    private BigDecimal depreciation;

    /**
     * 电费
     */
    private BigDecimal electricityCost;

    /**
     * 总成本
     */
    private BigDecimal totalCost;

    private Double depreciationRate; // 折旧率（百分比）

    private Double electricityRate; // 电费率（元/小时）
}