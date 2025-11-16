package com.example.atm.service.impl;

import com.example.atm.entity.Transaction;
import com.example.atm.mapper.TransactionMapper;
import com.example.atm.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper mapper;

    public TransactionServiceImpl(TransactionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void record(String card, String type, double amount, String remark) {
        Transaction t = new Transaction();
        t.setCard(card);
        t.setType(type);
        t.setAmount(amount);
        t.setRemark(remark);
        t.setTime(LocalDateTime.now());
        mapper.insert(t);
    }

    @Override
    public List<Transaction> getByCard(String card) {
        return mapper.findByCard(card);
    }
}
