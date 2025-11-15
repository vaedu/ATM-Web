package com.example.atm.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Transaction {

    private Long id;
    private String card;
    private String type;
    private double amount;
    private String remark;
    private LocalDateTime time;

}
