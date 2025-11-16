package com.example.atm.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String password;
    private double balance;
    private double limit;
    private String sex;
}
