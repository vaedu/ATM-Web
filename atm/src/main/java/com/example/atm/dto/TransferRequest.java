package com.example.atm.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {
    private String fromCard;
    private String toCard;
    private double amount;
    private String password;

}
