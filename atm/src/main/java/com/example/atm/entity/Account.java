package com.example.atm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 与数据库 account 表一一对应的实体类。
 * 数据库列: card, name, password, balance, `limit`, sex
 * 为了前端兼容，我们在 getter 上用 @JsonProperty 导出别名：
 *  - sex -> gender
 *  - limit -> dailyLimit
 * 不使用 Lombok，显式写 getter/setter，避免 IDE 或构建环境缺 lombok 的问题。
 */
@Setter
public class Account implements Serializable {

    @Getter
    private String card;
    @Getter
    private String name;
    @Getter
    private String password;
    // 数据库列名是 sex
    private String sex;
    // 数据库列名是 `limit`
    private double limit;
    @Getter
    private double balance;

    public Account() {}

    public Account(String card, String name, String password, double balance, double limit, String sex) {
        this.card = card;
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.limit = limit;
        this.sex = sex;
    }

    // JSON 导出别名：前端使用 user.gender
    @JsonProperty("gender")
    public String getSex() { return sex; }

    // JSON 导出别名：前端使用 user.dailyLimit
    @JsonProperty("dailyLimit")
    public double getLimit() { return limit; }

}
