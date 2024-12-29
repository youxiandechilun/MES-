package com.example.entity;

import lombok.Data;

/**
 * 工作报告实体类
 */
@Data
public class WorkReport {

    /**
     * 报告ID
     */
    private Integer reportID;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品编号
     */
    private String productCode;

    /**
     * 车间
     */
    private String workshop;

    /**
     * 小组
     */
    private String team;

    /**
     * 工人
     */
    private String worker;

    /**
     * 完成数量
     */
    private Integer completedQuantity;

    /**
     * 损失数量
     */
    private Integer lossQuantity;

    /**
     * 下一步工序
     */
    private String nextProcess;

    /**
     * 日期
     */
    private String date;
}