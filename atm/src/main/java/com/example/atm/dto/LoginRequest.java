package com.example.atm.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String card;
    private String password;
}
