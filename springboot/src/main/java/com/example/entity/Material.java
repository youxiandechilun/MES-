package com.example.entity;

import lombok.Data;

@Data
public class Material {

    private Integer id;
    private String materialName;
    private String materialType;
    private String materialCode;
    private Integer quantity;
    private Double unitCost;
    private String materialCategory;

}
