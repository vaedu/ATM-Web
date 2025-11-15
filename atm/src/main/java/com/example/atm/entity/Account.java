package com.example.atm.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account {

    private String card;
    private String name;
    private String password;
    private double balance;

}
