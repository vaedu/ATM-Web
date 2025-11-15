package com.example.atm.service;

import com.example.atm.entity.Account;

public interface AccountService {

    Account register(Account account);

    Account login(String cardNumber, String password);

    boolean deposit(String cardNumber, double amount);

    boolean withdraw(String cardNumber, double amount, String password);

    boolean transfer(String fromCard, String toCard, double amount, String password);

    Account getInfo(String cardNumber);
}
