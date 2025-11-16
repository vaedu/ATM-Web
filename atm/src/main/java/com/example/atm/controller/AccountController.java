package com.example.atm.controller;

import com.example.atm.dto.DepositRequest;
import com.example.atm.dto.TransferRequest;
import com.example.atm.dto.WithdrawRequest;
import com.example.atm.entity.Account;
import com.example.atm.entity.Transaction;
import com.example.atm.service.AccountService;
import com.example.atm.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atm")
public class AccountController {
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> handleRuntime(RuntimeException ex) {
        Map<String, Object> r = new HashMap<>();
        r.put("success", false);
        r.put("message", ex.getMessage());
        return r;
    }

    private final AccountService service;
    private final TransactionService transactionService;

    public AccountController(AccountService service, TransactionService transactionService) {
        this.service = service;
        this.transactionService = transactionService;
    }

    @PostMapping("/login")
    public Account login(@RequestBody Account req) {
        return service.login(req.getCard(), req.getPassword());
    }


    @GetMapping("/info")
    public Account info(@RequestParam String card) {
        return service.getInfo(card);
    }

    @PostMapping("/deposit")
    public double deposit(@RequestBody DepositRequest req) {
        double bal = service.deposit(req);
        transactionService.record(
                req.getCard(),
                "DEPOSIT",
                req.getAmount(),
                "存款"
        );
        return bal;
    }

    @PostMapping("/withdraw")
    public double withdraw(@RequestBody WithdrawRequest req) {
        double bal = service.withdraw(req);
        transactionService.record(
                req.getCard(),
                "WITHDRAW",
                req.getAmount(),
                "取款"
        );
        return bal;
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest req) {
        service.transfer(req);
        return "OK";
    }

    @PostMapping("/password")
    public boolean changePwd(@RequestParam String card,
                             @RequestParam String oldPwd,
                             @RequestParam String newPwd) {
        return service.changePassword(card, oldPwd, newPwd);
    }

    @GetMapping("/transactions")
    public List<Transaction> transactions(@RequestParam String card) {
        return transactionService.getByCard(card);
    }
}
