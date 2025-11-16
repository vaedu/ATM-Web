package com.example.atm.service;

import com.example.atm.entity.Transaction;
import java.util.List;

public interface TransactionService {
//    void record(String card, String type, double amount, String remark);
    List<Transaction> list(String card);
}
