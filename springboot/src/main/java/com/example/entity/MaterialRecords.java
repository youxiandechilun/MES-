package com.example.entity;

import lombok.Data;

@Data
public class MaterialRecords {

    private Integer recordId; // 记录ID

    private Integer materialId; // 物料ID

    private String materialName; // 物料名称

    private String worker; // 工人姓名

    private String issuanceDate; // 领取日期，字符串类型

    private Integer quantityIssued; // 领取数量

    private String purpose; // 领取目的

}
