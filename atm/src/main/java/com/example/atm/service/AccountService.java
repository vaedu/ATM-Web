package com.example.atm.service;

import com.example.atm.entity.Account;

public interface AccountService {
    Account register(Account account);                // 开户（自动生成卡号）
    Account login(String cardNumber, String password);
    boolean deposit(String cardNumber, double amount);
    boolean withdraw(String cardNumber, double amount, String password); // 可加密码验证
    boolean transfer(String fromCard, String toCard, double amount, String password);
    Account getInfo(String cardNumber);

}
