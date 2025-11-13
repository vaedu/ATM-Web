package com.example.atm.service.impl;

import com.example.atm.entity.Account;
import com.example.atm.mapper.AccountMapper;
import com.example.atm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired(required = false)
    private AccountMapper mapper; // 如果用内存 DataBase，mapper 可能为 null

    private String generateCard() {
        // 银行卡号一般以 62 开头，后面随机生成14位，共16位
        StringBuilder sb = new StringBuilder("62");
        for (int i = 0; i < 14; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }


    @Override
    public Account register(Account account) {
        if (account.getCard() == null || account.getCard().isEmpty()) {
            String card;
            do {
                card = generateCard();
            } while (mapper != null && mapper.findByCardNumber(card) != null);
            account.setCard(card);
        }
        mapper.insert(account);
        return mapper.findByCardNumber(account.getCard());
    }



    @Override
    public Account login(String cardNumber, String password) {
        Account acc = mapper != null ? mapper.findByCardNumber(cardNumber) : null;
        if (acc != null && acc.getPassword().equals(password)) return acc;
        return null;
    }

    @Override
    public boolean deposit(String cardNumber, double amount) {
        if (amount <= 0) return false;
        Account acc = mapper != null ? mapper.findByCardNumber(cardNumber) : null;
        if (acc == null) return false;
        double newBal = acc.getBalance() + amount;
        if (mapper != null) mapper.updateBalance(cardNumber, newBal);
        acc.setBalance(newBal);
        return true;
    }

    @Override
    public boolean withdraw(String cardNumber, double amount, String password) {
        if (amount <= 0) return false;
        Account acc = mapper != null ? mapper.findByCardNumber(cardNumber) : null;
        if (acc == null) return false;
        if (!acc.getPassword().equals(password)) return false;
        if (amount > acc.getBalance()) return false;
        if (amount > acc.getLimit()) return false;
        double newBal = acc.getBalance() - amount;
        if (mapper != null) mapper.updateBalance(cardNumber, newBal);
        acc.setBalance(newBal);
        return true;
    }

    @Override
    public boolean transfer(String fromCard, String toCard, double amount, String password) {
        if (amount <= 0) return false;
        Account from = mapper != null ? mapper.findByCardNumber(fromCard) : null;
        Account to = mapper != null ? mapper.findByCardNumber(toCard) : null;
        if (from == null || to == null) return false;
        if (!from.getPassword().equals(password)) return false;
        if (amount > from.getBalance()) return false;
        double newFrom = from.getBalance() - amount;
        double newTo = to.getBalance() + amount;
        if (mapper != null) {
            mapper.updateBalance(fromCard, newFrom);
            mapper.updateBalance(toCard, newTo);
        }
        from.setBalance(newFrom);
        to.setBalance(newTo);
        return true;
    }

    @Override
    public Account getInfo(String cardNumber) {
        return mapper != null ? mapper.findByCardNumber(cardNumber) : null;
    }
}
