package com.example.atm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {

    private String card;
    private String name;
    private String password;
    private double balance;
    private double limit;
    // ✅ 必须叫 getSex
    // ✅ 必须叫 setSex
    private String sex; // ✅ 这里必须叫 sex

    public Account() {
    }

    public Account(String card, String name, String password, double balance, double limit, String sex) {
        this.card = card;
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.limit = limit;
        this.sex = sex;
    }

}
