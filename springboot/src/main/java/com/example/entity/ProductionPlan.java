package com.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class ProductionPlan {

    private Integer id;                          // 主键
    private String planID;                       // 计划编号
    private String orderID;                      // 订单编号，
    private String customerID;                   // 客户编号，
    private String productName;                  // 产品名称
    private String productSum;                   // 产品数量，
    private String startTime;                    // 计划开始时间
    private String endTime;                      // 计划结束时间
    private String status;                       // 计划状态
    private String totalAmount;                  // 总金额
    private String remarks;                      // 备注信息

    // 生产流程相关信息
    private String productionProcesses; // 产品的生产流程列表

    // 生产设备相关信息
    private String productionEquipments; // 生产设备列表

    // 所需物料相关信息
    private String materials; // 所需物料列表

    // 加工车间相关信息
    private String workshop; // 加工车间

}

