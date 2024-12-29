package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class Order {

    private Integer id;                        // 主键
    private Integer orderID;                   // 订单编号
    private Integer customerID;                // 客户编号
    private String contractNumber;             // 合同编号
    private String productName;                // 产品名称
    private String orderType;                  // 订单类型
    private String generateProductionPlan;     // 是否生成生产计划 1是 0否
    private BigDecimal productSum;             // 产品数量
    private String startTime;                  // 开始时间
    private String endTime;                    // 结束时间
    private String status;                     // 订单状态
    private BigDecimal totalAmount;            // 总金额4
}