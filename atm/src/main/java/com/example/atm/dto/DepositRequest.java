package com.example.atm.dto;

import lombok.Data;

@Data
public class DepositRequest {
    private String card;
    private double amount;
}
