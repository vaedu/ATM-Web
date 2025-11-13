package com.example.atm.controller;

import com.example.atm.common.Result;
import com.example.atm.entity.Account;
import com.example.atm.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/atm")
@CrossOrigin
public class AccountController {

    private final AccountService service;
    public AccountController(AccountService service) { this.service = service; }

    @PostMapping("/open")
    public Result open(@RequestParam String name,
                       @RequestParam String password,
                       @RequestParam double balance,
                       @RequestParam double limit,
                       @RequestParam String sex) {

        // ① 密码必须是 6 位数字
        if (password == null || !password.matches("^\\d{6}$")) {
            return Result.error("密码必须是 6 位纯数字！");
        }

        // ② 生成 16 位银行卡号
        String card = generateCard();

        // ③ 构造 Account 对象
        Account a = new Account();
        a.setCard(card);
        a.setName(name);
        a.setPassword(password);
        a.setBalance(balance);
        a.setLimit(limit);
        a.setSex(sex);

        // ④ 调用 Service 存入数据库
        Account created = service.register(a);

        // ⑤ 返回成功 JSON
        return Result.success(created);
    }


    @PostMapping("/login")
    public Result login(@RequestParam String card,
                        @RequestParam String password) {
        Account acc = service.login(card, password);
        if (acc == null) {
            return Result.error("卡号或密码错误！");
        }
        return Result.success(acc);
    }


    @PostMapping("/deposit")
    public Result deposit(@RequestParam String card,
                          @RequestParam double amount) {
        return service.deposit(card, amount)
                ? Result.success("存款成功")
                : Result.error("存款失败");
    }


    @PostMapping("/withdraw")
    public Result withdraw(@RequestParam String card,
                           @RequestParam double amount,
                           @RequestParam String password) {
        return service.withdraw(card, amount, password)
                ? Result.success("取款成功")
                : Result.error("取款失败");
    }


    @PostMapping("/transfer")
    public Result transfer(@RequestParam String fromCard,
                           @RequestParam String toCard,
                           @RequestParam double amount,
                           @RequestParam String password) {
        return service.transfer(fromCard, toCard, amount, password)
                ? Result.success("转账成功")
                : Result.error("转账失败");
    }


    @GetMapping("/info")
    public Result info(@RequestParam String card) {
        Account acc = service.getInfo(card);
        return acc != null ? Result.success(acc) : Result.error("查询失败");
    }


    private String generateCard() {
        StringBuilder sb = new StringBuilder("6222");
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        while (sb.length() < 16) {
            sb.append(rnd.nextInt(0, 10));
        }
        return sb.toString();
    }
}
