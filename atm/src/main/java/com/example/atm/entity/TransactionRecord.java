package com.example.atm.entity;

import java.time.LocalDateTime;

public class TransactionRecord {
    private int id;
    private String card;
    private String type;
    private double amount;
    private String targetCard;
    private LocalDateTime time;
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTargetCard() {
        return targetCard;
    }

    public void setTargetCard(String targetCard) {
        this.targetCard = targetCard;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
