package com.example.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String role;
}
