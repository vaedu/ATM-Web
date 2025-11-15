package com.example.atm.controller;

import com.example.atm.dto.DepositRequest;
import com.example.atm.dto.TransferRequest;
import com.example.atm.dto.WithdrawRequest;
import com.example.atm.entity.Account;
import com.example.atm.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atm")
@CrossOrigin
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public Account login(@RequestBody Account account) {
        return service.login(account.getCard(), account.getPassword());
    }

    @PostMapping("/register")
    public Account register(@RequestBody Account acc) {
        acc.setBalance(0);
        return service.register(acc);
    }

    @PostMapping("/deposit")
    public double deposit(@RequestBody DepositRequest req) {
        return service.deposit(req);
    }

    @PostMapping("/withdraw")
    public double withdraw(@RequestBody WithdrawRequest req) {
        return service.withdraw(req);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest req) {
        service.transfer(req);
        return "success";
    }

    @PostMapping("/changePassword")
    public boolean changePassword(
            @RequestParam String card,
            @RequestParam String oldPwd,
            @RequestParam String newPwd
    ) {
        return service.changePassword(card, oldPwd, newPwd);
    }
}
