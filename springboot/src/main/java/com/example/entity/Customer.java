package com.example.entity;

import lombok.Data;

@Data
public class Customer {
    private Integer id;
    private String contractNumber;
    private String contractName;
    private String customerCode;
    private String customerName;
    private String customerPhone;
}
