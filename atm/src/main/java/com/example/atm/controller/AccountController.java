package com.example.atm.controller;

import com.example.atm.dto.DepositRequest;
import com.example.atm.dto.WithdrawRequest;
import com.example.atm.entity.Account;
import com.example.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/atm")
@CrossOrigin
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }
    @Autowired
    private AccountService accountService;

    // =======================
    // 1. 开户 —— JSON Body
    // =======================
    @PostMapping("/open")
    public Account open(@RequestBody Map<String, Object> req) {

        String name = (String) req.get("name");
        String password = (String) req.get("password");
        double balance = ((Number) req.get("balance")).doubleValue();
        double limit = ((Number) req.get("limit")).doubleValue();
        String sex = (String) req.get("sex");

        // 密码必须为 6 位数字
        if (!password.matches("^\\d{6}$")) {
            return null;
        }

        Account a = new Account();
        a.setName(name);
        a.setPassword(password);
        a.setBalance(balance);
        a.setLimit(limit);
        a.setSex(sex);

        return service.register(a);
    }

    // =======================
    // 2. 登录 —— JSON Body
    // =======================
    @PostMapping("/login")
    public Account login(@RequestBody Map<String, Object> req) {

        String card = (String) req.get("card");
        String password = (String) req.get("password");

        return service.login(card, password);
    }

    // =======================
    // 3. 存款 —— JSON Body
    // =======================
    @PostMapping("/deposit")
    public boolean deposit(@RequestBody DepositRequest request) {
        return accountService.deposit(request.getCard(), request.getAmount());
    }


    // =======================
    // 4. 取款 —— JSON Body
    // =======================
    @PostMapping("/withdraw")
    public boolean withdraw(@RequestBody WithdrawRequest request) {
        return accountService.withdraw(request.getCard(), request.getAmount(), request.getPassword());
    }

    // =======================
    // 5. 转账 —— JSON Body
    // =======================
    @PostMapping("/transfer")
    public boolean transfer(@RequestBody Map<String, Object> req) {

        String from = (String) req.get("fromCard");
        String to = (String) req.get("toCard");
        double amount = ((Number) req.get("amount")).doubleValue();
        String password = (String) req.get("password");

        return service.transfer(from, to, amount, password);
    }

    // =======================
    // 6. 查询账户 —— GET 无需改
    // =======================
    @GetMapping("/info")
    public Account info(@RequestParam String card) {
        return service.getInfo(card);
    }
}
