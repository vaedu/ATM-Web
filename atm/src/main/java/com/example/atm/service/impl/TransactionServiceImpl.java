package com.example.atm.service.impl;

import com.example.atm.entity.Transaction;
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
    public void record(String card, String type, double amount, String remark) {
        if (mapper == null) return;
        Transaction tx = new Transaction();
        tx.setCard(card);
        tx.setType(type);
        tx.setAmount(amount);
        tx.setRemark(remark);
        mapper.insert(tx);
    }

    @Override
    public List<Transaction> list(String card) {
        return mapper != null ? mapper.findByCard(card) : List.of();
    }
}
