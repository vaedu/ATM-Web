package com.example.atm.service.impl;

import com.example.atm.dto.*;
import com.example.atm.entity.Account;
import com.example.atm.mapper.AccountMapper;
import com.example.atm.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper mapper;

    public AccountServiceImpl(AccountMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Account login(String card, String password) {
        Account a = mapper.findByCard(card);
        if (a == null) throw new RuntimeException("卡号不存在");
        if (!a.getPassword().equals(password)) throw new RuntimeException("密码错误");
        return a;
    }

    @Override
    public Account register(Account account) {
        mapper.insert(account);
        return account;
    }

    @Override
    public double deposit(DepositRequest req) {
        Account a = mapper.findByCard(req.getCard());
        if (a == null) throw new RuntimeException("账户不存在");

        double newBalance = a.getBalance() + req.getAmount();
        mapper.updateBalance(req.getCard(), newBalance);

        return newBalance;
    }

    @Override
    public double withdraw(WithdrawRequest req) {
        Account a = mapper.findByCard(req.getCard());
        if (a == null) throw new RuntimeException("账户不存在");

        if (!a.getPassword().equals(req.getPassword()))
            throw new RuntimeException("密码错误");

        if (a.getBalance() < req.getAmount())
            throw new RuntimeException("余额不足");

        double newBalance = a.getBalance() - req.getAmount();
        mapper.updateBalance(req.getCard(), newBalance);

        return newBalance;
    }

    @Override
    public void transfer(TransferRequest req) {
        Account from = mapper.findByCard(req.getFromCard());
        Account to = mapper.findByCard(req.getToCard());

        if (from == null) throw new RuntimeException("转出账号不存在");
        if (to == null) throw new RuntimeException("收款账号不存在");
        if (!from.getPassword().equals(req.getPassword()))
            throw new RuntimeException("密码错误");
        if (from.getBalance() < req.getAmount())
            throw new RuntimeException("余额不足");

        mapper.updateBalance(req.getFromCard(), from.getBalance() - req.getAmount());
        mapper.updateBalance(req.getToCard(), to.getBalance() + req.getAmount());
    }

    @Override
    public boolean changePassword(String card, String oldPwd, String newPwd) {
        Account a = mapper.findByCard(card);
        if (a == null) throw new RuntimeException("用户不存在");
        if (!a.getPassword().equals(oldPwd)) throw new RuntimeException("旧密码错误");

        mapper.updatePassword(card, newPwd);
        return true;
    }

    @Override
    public Account getInfo(String card) {
        return mapper.findByCard(card);
    }
}
