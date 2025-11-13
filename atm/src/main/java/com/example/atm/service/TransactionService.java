package com.example.atm.service;

import com.example.atm.entity.TransactionRecord;
import java.util.List;

public interface TransactionService {
    void addRecord(TransactionRecord record);
    List<TransactionRecord> getRecordsByCard(String card);
}
