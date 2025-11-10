package com.example.atm.controller;

import com.example.atm.entity.Account;
import com.example.atm.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/atm")
@CrossOrigin
public class AccountController {

    private final AccountService service;
    public AccountController(AccountService service) { this.service = service; }

    @PostMapping("/open")
    public Account open(@RequestParam String name,
                        @RequestParam String password,
                        @RequestParam double balance,
                        @RequestParam double limit,
                        @RequestParam String sex) {
        String card = UUID.randomUUID().toString().substring(0, 8);
        Account a = new Account(name, card, password, balance, limit, sex);
        return service.register(a);
    }

    @PostMapping("/login")
    public Account login(@RequestParam String card,
                         @RequestParam String password) {
        return service.login(card, password);
    }

    @PostMapping("/deposit")
    public boolean deposit(@RequestParam String card,
                           @RequestParam double amount) {
        return service.deposit(card, amount);
    }

    @PostMapping("/withdraw")
    public boolean withdraw(@RequestParam String card,
                            @RequestParam double amount,
                            @RequestParam String password) {
        return service.withdraw(card, amount, password);
    }

    @PostMapping("/transfer")
    public boolean transfer(@RequestParam String fromCard,
                            @RequestParam String toCard,
                            @RequestParam double amount,
                            @RequestParam String password) {
        return service.transfer(fromCard, toCard, amount, password);
    }

    @GetMapping("/info")
    public Account info(@RequestParam String card) {
        return service.getInfo(card);
    }
}
