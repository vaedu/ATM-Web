package com.example.atm.service.impl;

import com.example.atm.entity.TransactionRecord;
import com.example.atm.mapper.TransactionMapper;
import com.example.atm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired(required = false)
    private TransactionMapper mapper;

    @Override
    public void addRecord(TransactionRecord record) {
        if (mapper != null) mapper.insert(record);
    }

    @Override
    public List<TransactionRecord> getRecordsByCard(String card) {
        return mapper != null ? mapper.findByCard(card) : List.of();
    }
}
