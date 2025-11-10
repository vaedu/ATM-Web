package com.example.atm.entity;

public class Account {

    private String card;
    private String name;
    private String password;
    private double balance;
    private double limit;
    private String sex; // ✅ 这里必须叫 sex

    public Account(String card, String name, String password, double balance, double limit, String sex) {
        this.card = card;
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.limit = limit;
        this.sex = sex;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public String getSex() {  // ✅ 必须叫 getSex
        return sex;
    }

    public void setSex(String sex) { // ✅ 必须叫 setSex
        this.sex = sex;
    }
}
