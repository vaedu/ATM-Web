package com.example.atm.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String fromCard;
    private String toCard;
    private double amount;
    private String password;
}
