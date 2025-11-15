package com.example.atm.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {

    private int id;
    private String card;
    private String type;
    private double amount;
    private LocalDateTime time;
    private String remark;

    public Transaction() {}

    public Transaction(String card, String type, double amount, LocalDateTime time) {
        this.card = card;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

}
