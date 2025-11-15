package com.example.atm.controller;

import com.example.atm.entity.Transaction;
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

    @GetMapping("/list")
    public List<Transaction> list(@RequestParam String card) {
        return service.list(card);
    }
}
