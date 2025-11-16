package com.example.atm.controller;

import com.example.atm.entity.Account;
import com.example.atm.service.AccountService;
import org.springframework.web.bind.annotation.*;

/**
 * ATM REST Controller
 */
@RestController
@RequestMapping("/api/atm")
@CrossOrigin
public class AccountController {

    private final AccountService service;
    public AccountController(AccountService service) {
        this.service = service;
    }

    /**
     * 开户（前端调用 /open）
     * 参数：name, password, balance, limit, sex
     * 返回：Account（包含生成的 card）
     */
    @PostMapping("/open")
    public Account open(@RequestParam String name,
                        @RequestParam String password,
                        @RequestParam(required = false, defaultValue = "0") double balance,
                        @RequestParam(required = false, defaultValue = "1000") double limit,
                        @RequestParam(required = false, defaultValue = "男") String sex) {

        // 密码规则：6 位纯数字（你之前要求）
        if (password == null || !password.matches("^\\d{6}$")) {
            throw new IllegalArgumentException("密码必须是6位纯数字");
        }

        Account a = new Account();
        a.setCard(null); // 交由 service 生成
        a.setName(name);
        a.setPassword(password);
        a.setBalance(balance);
        a.setLimit(limit);
        a.setSex(sex);

        return service.register(a);
    }

    @PostMapping("/login")
    public Account login(@RequestBody Account req) {
        return service.login(req.getCard(), req.getPassword());
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

    @PostMapping("/changePassword")
    public boolean changePassword(@RequestParam String card,
                                  @RequestParam String oldPassword,
                                  @RequestParam String newPassword) {
        return service.changePassword(card, oldPassword, newPassword);
    }
}
