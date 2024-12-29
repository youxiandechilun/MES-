package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Employees {


    private Integer id;


    private String no;

    private String username;


    private String password;


    private String name;


    private String workshop;


    private String teamsGroups;


    private String sex;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date seniority;


    private int grade;


    private String avatar;


    private String history;


    private String role;


}