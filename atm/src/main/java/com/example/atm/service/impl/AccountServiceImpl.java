package com.example.atm.service.impl;

import com.example.atm.entity.Account;
import com.example.atm.entity.Transaction;
import com.example.atm.mapper.AccountMapper;
import com.example.atm.mapper.TransactionMapper;
import com.example.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper mapper;

    @Autowired
    private TransactionMapper tmapper;

    private String generateCard() {
        StringBuilder sb = new StringBuilder("6222");
        for (int i = 0; i < 12; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }

    @Override
    public Account register(Account account) {

        String card;
        do {
            card = generateCard();
        } while (mapper.findByCardNumber(card) != null);

        account.setCard(card);
        mapper.insert(account);

        // 写入开户记录
        Transaction t = new Transaction(card, "开户", account.getBalance(), LocalDateTime.now());
        tmapper.insert(t);

        return mapper.findByCardNumber(card);
    }

    @Override
    public Account login(String cardNumber, String password) {
        Account acc = mapper.findByCardNumber(cardNumber);
        if (acc != null && acc.getPassword().equals(password)) {
            return acc;
        }
        return null;
    }

    @Override
    public boolean deposit(String cardNumber, double amount) {
        if (amount <= 0) return false;

        Account acc = mapper.findByCardNumber(cardNumber);
        if (acc == null) return false;

        double newBal = acc.getBalance() + amount;
        mapper.updateBalance(cardNumber, newBal);

        // 记录交易
        tmapper.insert(new Transaction(cardNumber, "存款", amount, LocalDateTime.now()));

        return true;
    }

    @Override
    public boolean withdraw(String cardNumber, double amount, String password) {
        if (amount <= 0) return false;

        Account acc = mapper.findByCardNumber(cardNumber);
        if (acc == null) return false;
        if (!acc.getPassword().equals(password)) return false;
        if (acc.getBalance() < amount) return false;

        double newBal = acc.getBalance() - amount;
        mapper.updateBalance(cardNumber, newBal);

        tmapper.insert(new Transaction(cardNumber, "取款", amount, LocalDateTime.now()));

        return true;
    }

    @Override
    public boolean transfer(String fromCard, String toCard, double amount, String password) {

        Account from = mapper.findByCardNumber(fromCard);
        Account to = mapper.findByCardNumber(toCard);

        if (from == null || to == null) return false;
        if (!from.getPassword().equals(password)) return false;
        if (from.getBalance() < amount) return false;

        mapper.updateBalance(fromCard, from.getBalance() - amount);
        mapper.updateBalance(toCard, to.getBalance() + amount);

        tmapper.insert(new Transaction(fromCard, "向 " + toCard + " 转账", amount, LocalDateTime.now()));
        tmapper.insert(new Transaction(toCard, "收到来自 " + fromCard + " 转账", amount, LocalDateTime.now()));

        return true;
    }

    @Override
    public Account getInfo(String cardNumber) {
        return mapper.findByCardNumber(cardNumber);
    }
}
