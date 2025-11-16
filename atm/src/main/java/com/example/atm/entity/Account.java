package com.example.atm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Account {

    private String card;
    private String name;
    private String password;

    // 数据库字段是 sex，这里转成 gender 给前端用
    @JsonProperty("gender")
    private String gender;

    // 数据库字段是 limit，这里转成 dailyLimit 给前端用
    @JsonProperty("dailyLimit")
    private double dailyLimit;

    private double balance;
}
