package com.example.entity;

import lombok.Data;

@Data
public class Tickets {

    private Integer id;                          // 主键 自增
    private String ticketID;                     // 工单编号
    private String planID;                       // 计划编号
    private String productName;                  // 产品名称
    private String workshop;                     // 车间
    private String team;                         // 班组
    private String worker;                       // 工人名字
    private String grade;                        // 工人工级
    private String process;                      // 工序
    private String number;                       // 应该完成的数量
    private String startDate;                    // 开始日期
    private String endDate;                      // 结束日期

}
