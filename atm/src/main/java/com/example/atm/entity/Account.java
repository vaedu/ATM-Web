package com.example.atm.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 与数据库 account 表一一对应的实体类。
 * 数据库列: card, name, password, balance, `limit`, sex
 * 返回给前端时字段名保持:
 *   sex -> sex
 *   limit -> limit
 * 这样前端 Home.vue 能直接读取 info.sex 和 info.limit
 */
@Getter
@Setter
public class Account implements Serializable {

    private String card;
    private String name;
    private String password;

    // 数据库 sex 字段
    private String sex;

    // 数据库 limit 字段
    private double limit;

    private double balance;

    public Account() {}

    public Account(String card, String name, String password,
                   double balance, double limit, String sex) {
        this.card = card;
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.limit = limit;
        this.sex = sex;
    }
}
