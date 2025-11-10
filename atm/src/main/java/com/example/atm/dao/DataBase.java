package com.example.atm.dao;

import com.example.atm.entity.Account;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static final List<Account> users = new ArrayList<>();

    public static Account findByCardNumber(String cardNumber) {
        if (cardNumber == null) return null;
        for (Account acc : users) {
            if (cardNumber.equals(acc.getCard())) {
                return acc;
            }
        }
        return null;
    }

    public static boolean cardExists(String cardNumber) {
        return findByCardNumber(cardNumber) != null;
    }
}
