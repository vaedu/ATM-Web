package com.example.atm.dto;

import lombok.Data;

@Data
public class ChangePwdRequest {
    private String card;
    private String oldPwd;
    private String newPwd;
}
