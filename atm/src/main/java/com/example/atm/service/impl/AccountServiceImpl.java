package com.example.atm.service.impl;

import com.example.atm.entity.Account;
import com.example.atm.entity.Transaction;
import com.example.atm.mapper.AccountMapper;
import com.example.atm.mapper.TransactionMapper;
import com.example.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired(required = false)
    private AccountMapper accountMapper;

    // 如果你的项目中有 TransactionMapper（插入交易记录），会被注入；否则为 null
    @Autowired(required = false)
    private TransactionMapper transactionMapper;

    // 生成 16 位纯数字银行卡号（以6222开头）
    private String generateCard() {
        StringBuilder sb = new StringBuilder("6222");
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        while (sb.length() < 16) {
            sb.append(rnd.nextInt(0, 10));
        }
        return sb.toString();
    }

    @Override
    public Account register(Account account) {
        // 若无卡号则生成，确保唯一（简单重试）
        if (account.getCard() == null || account.getCard().isEmpty()) {
            String card;
            do {
                card = generateCard();
            } while (accountMapper != null && accountMapper.findByCard(card) != null);
            account.setCard(card);
        }

        if (account.getBalance() == 0.0) account.setBalance(0.0);

        if (accountMapper != null) {
            accountMapper.insert(account);
            // 返回数据库中的对象（为了确保字段一致）
            return accountMapper.findByCard(account.getCard());
        } else {
            // 如果没有 mapper（例如你正在使用内存 DB），直接返回对象
            return account;
        }
    }

    @Override
    public Account login(String cardNumber, String password) {
        if (accountMapper == null) return null;
        Account acc = accountMapper.findByCard(cardNumber);
        if (acc != null && acc.getPassword().equals(password)) return acc;
        return null;
    }

    @Override
    public boolean deposit(String cardNumber, double amount) {
        if (amount <= 0) return false;
        if (accountMapper == null) return false;

        Account acc = accountMapper.findByCard(cardNumber);
        if (acc == null) return false;

        double newBal = acc.getBalance() + amount;
        accountMapper.updateBalance(cardNumber, newBal);

        // 如果有 transactionMapper，则写入一条交易记录
        if (transactionMapper != null) {
            Transaction t = new Transaction();
            t.setCard(cardNumber);
            t.setType("DEPOSIT");
            t.setAmount(amount);
            t.setRemark("存款");
            t.setTime(LocalDateTime.now());
            transactionMapper.insert(t);
        }

        return true;
    }

    @Override
    public boolean withdraw(String cardNumber, double amount, String password) {
        if (amount <= 0) return false;
        if (accountMapper == null) return false;

        Account acc = accountMapper.findByCard(cardNumber);
        if (acc == null) return false;
        if (!acc.getPassword().equals(password)) return false;
        if (amount > acc.getBalance()) return false;
        if (amount > acc.getLimit()) return false;

        double newBal = acc.getBalance() - amount;
        accountMapper.updateBalance(cardNumber, newBal);

        if (transactionMapper != null) {
            Transaction t = new Transaction();
            t.setCard(cardNumber);
            t.setType("WITHDRAW");
            t.setAmount(amount);
            t.setRemark("取款");
            t.setTime(LocalDateTime.now());
            transactionMapper.insert(t);
        }

        return true;
    }

    @Override
    public boolean transfer(String fromCard, String toCard, double amount, String password) {
        if (amount <= 0) return false;
        if (accountMapper == null) return false;

        Account from = accountMapper.findByCard(fromCard);
        Account to = accountMapper.findByCard(toCard);
        if (from == null || to == null) return false;
        if (!from.getPassword().equals(password)) return false;
        if (amount > from.getBalance()) return false;

        double newFrom = from.getBalance() - amount;
        double newTo = to.getBalance() + amount;

        accountMapper.updateBalance(fromCard, newFrom);
        accountMapper.updateBalance(toCard, newTo);

        if (transactionMapper != null) {
            Transaction t1 = new Transaction();
            t1.setCard(fromCard);
            t1.setType("TRANSFER_OUT");
            t1.setAmount(amount);
            t1.setRemark("转账给 " + toCard);
            t1.setTime(LocalDateTime.now());
            transactionMapper.insert(t1);

            Transaction t2 = new Transaction();
            t2.setCard(toCard);
            t2.setType("TRANSFER_IN");
            t2.setAmount(amount);
            t2.setRemark("来自 " + fromCard);
            t2.setTime(LocalDateTime.now());
            transactionMapper.insert(t2);
        }

        return true;
    }

    @Override
    public boolean changePassword(String card, String oldPwd, String newPwd) {
        if (accountMapper == null) return false;
        int updated = accountMapper.updatePassword(card, oldPwd, newPwd);
        return updated > 0;
    }

    @Override
    public Account getInfo(String cardNumber) {
        if (accountMapper == null) return null;
        return accountMapper.findByCard(cardNumber);
    }
}
