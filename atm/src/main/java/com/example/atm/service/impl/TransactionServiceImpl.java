package com.example.atm.service.impl;

import com.example.atm.entity.Transaction;
import com.example.atm.mapper.TransactionMapper;
import com.example.atm.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper mapper;

    public TransactionServiceImpl(TransactionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Transaction> list(String card) {
        return mapper.findByCard(card);
    }
}
