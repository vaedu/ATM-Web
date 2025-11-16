package com.example.atm.controller;

import com.example.atm.dto.*;
import com.example.atm.entity.Account;
import com.example.atm.entity.Transaction;
import com.example.atm.service.AccountService;
import com.example.atm.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atm")
public class AccountController {

    private final AccountService accountService;
    private final TransactionService txService;

    public AccountController(AccountService accountService, TransactionService txService) {
        this.accountService = accountService;
        this.txService = txService;
    }

    // 登录
    @PostMapping("/login")
    public Account login(@RequestBody LoginRequest req) {
        return accountService.login(req.getCard(), req.getPassword());
    }

    // 自助开户
    @PostMapping("/register")
    public Account register(@RequestBody Account acc) {
        return accountService.register(acc);
    }

    // 获取账户信息（主页用）
    @GetMapping("/info")
    public Account info(@RequestParam String card) {
        return accountService.getInfo(card);
    }

    // 获取交易记录（主页用）
    @GetMapping("/transactions")
    public List<Transaction> tx(@RequestParam String card) {
        return txService.list(card);
    }

    // 存款
    @PostMapping("/deposit")
    public double deposit(@RequestBody DepositRequest req) {
        return accountService.deposit(req);
    }

    // 取款
    @PostMapping("/withdraw")
    public double withdraw(@RequestBody WithdrawRequest req) {
        return accountService.withdraw(req);
    }

    // 转账
    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequest req) {
        accountService.transfer(req);
    }

    // 修改密码
    @PostMapping("/changePwd")
    public boolean changePwd(@RequestBody ChangePwdRequest req) {
        return accountService.changePassword(req.getCard(), req.getOldPwd(), req.getNewPwd());
    }
}
