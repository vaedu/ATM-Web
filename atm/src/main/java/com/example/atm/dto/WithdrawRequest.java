package com.example.atm.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WithdrawRequest {
    private String card;
    private double amount;
    private String password;

}

