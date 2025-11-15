package com.example.atm.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepositRequest {
    private String card;
    private double amount;

}
