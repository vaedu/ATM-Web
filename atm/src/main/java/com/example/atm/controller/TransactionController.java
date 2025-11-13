package com.example.atm.controller;

import com.example.atm.entity.TransactionRecord;
import com.example.atm.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    // 新增交易记录
    @PostMapping("/add")
    public boolean add(@RequestBody TransactionRecord record) {
        service.addRecord(record);
        return true;
    }

    // 查询某个账户的交易记录
    @GetMapping("/list")
    public List<TransactionRecord> list(@RequestParam String card) {
        return service.getRecordsByCard(card);
    }
}
