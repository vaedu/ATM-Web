package com.example.atm.service.impl;

import com.example.atm.dto.DepositRequest;
import com.example.atm.dto.TransferRequest;
import com.example.atm.dto.WithdrawRequest;
import com.example.atm.entity.Account;
import com.example.atm.entity.Transaction;
import com.example.atm.mapper.AccountMapper;
import com.example.atm.mapper.TransactionMapper;
import com.example.atm.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    public AccountServiceImpl(AccountMapper accountMapper, TransactionMapper transactionMapper) {
        this.accountMapper = accountMapper;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Account login(String card, String password) {
        Account a = accountMapper.findByCard(card);
        if (a == null || !a.getPassword().equals(password)) {
            return null;
        }
        return a;
    }

    @Override
    public Account register(Account account) {
        accountMapper.insert(account);
        return account;
    }

    @Override
    public double deposit(DepositRequest request) {
        Account acc = accountMapper.findByCard(request.getCard());
        if (acc == null) throw new RuntimeException("卡号不存在");

        double newBal = acc.getBalance() + request.getAmount();
        accountMapper.updateBalance(acc.getCard(), newBal);

        Transaction t = new Transaction();
        t.setCard(acc.getCard());
        t.setType("DEPOSIT");
        t.setAmount(request.getAmount());
        t.setRemark("存款");
        t.setTime(LocalDateTime.now());
        transactionMapper.insert(t);

        return newBal;
    }

    @Override
    public double withdraw(WithdrawRequest request) {

        Account acc = accountMapper.findByCard(request.getCard());
        if (acc == null) throw new RuntimeException("卡号不存在");
        if (!acc.getPassword().equals(request.getPassword())) throw new RuntimeException("密码错误");
        if (acc.getBalance() < request.getAmount()) throw new RuntimeException("余额不足");

        double newBal = acc.getBalance() - request.getAmount();
        accountMapper.updateBalance(acc.getCard(), newBal);

        Transaction t = new Transaction();
        t.setCard(acc.getCard());
        t.setType("WITHDRAW");
        t.setAmount(request.getAmount());
        t.setRemark("取款");
        t.setTime(LocalDateTime.now());
        transactionMapper.insert(t);

        return newBal;
    }

    @Override
    public void transfer(TransferRequest request) {

        Account from = accountMapper.findByCard(request.getFromCard());
        Account to = accountMapper.findByCard(request.getToCard());

        if (from == null || to == null) throw new RuntimeException("卡号不存在");
        if (!from.getPassword().equals(request.getPassword())) throw new RuntimeException("密码错误");
        if (from.getBalance() < request.getAmount()) throw new RuntimeException("余额不足");

        double newFromBal = from.getBalance() - request.getAmount();
        double newToBal = to.getBalance() + request.getAmount();

        accountMapper.updateBalance(from.getCard(), newFromBal);
        accountMapper.updateBalance(to.getCard(), newToBal);

        Transaction t1 = new Transaction();
        t1.setCard(from.getCard());
        t1.setType("TRANSFER_OUT");
        t1.setAmount(request.getAmount());
        t1.setRemark("转账给 " + to.getCard());
        t1.setTime(LocalDateTime.now());
        transactionMapper.insert(t1);

        Transaction t2 = new Transaction();
        t2.setCard(to.getCard());
        t2.setType("TRANSFER_IN");
        t2.setAmount(request.getAmount());
        t2.setRemark("来自 " + from.getCard());
        t2.setTime(LocalDateTime.now());
        transactionMapper.insert(t2);
    }

    @Override
    public boolean changePassword(String card, String oldPwd, String newPwd) {
        Account acc = accountMapper.findByCard(card);
        if (acc == null) return false;
        if (!acc.getPassword().equals(oldPwd)) return false;

        accountMapper.updatePassword(card, oldPwd, newPwd);
        return true;
    }

    @Override
    public Account getInfo(String card) {
        return accountMapper.findByCard(card);
    }
}
