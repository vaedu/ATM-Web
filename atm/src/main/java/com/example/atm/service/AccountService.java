package com.example.atm.service;

import com.example.atm.dto.DepositRequest;
import com.example.atm.dto.WithdrawRequest;
import com.example.atm.dto.TransferRequest;
import com.example.atm.entity.Account;

public interface AccountService {

    Account login(String card, String password);

    Account register(Account account);

    double deposit(DepositRequest request);

    double withdraw(WithdrawRequest request);

    void transfer(TransferRequest request);

    boolean changePassword(String card, String oldPwd, String newPwd);
}
