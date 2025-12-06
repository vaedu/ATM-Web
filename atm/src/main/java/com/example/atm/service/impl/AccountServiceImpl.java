package com.example.atm.service.impl;

import com.example.atm.Utils.PasswordUtils;
import com.example.atm.dto.DepositRequest;
import com.example.atm.dto.TransferRequest;
import com.example.atm.dto.WithdrawRequest;
import com.example.atm.entity.Account;
import com.example.atm.mapper.AccountMapper;
import com.example.atm.service.AccountService;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountMapper mapper;

    public AccountServiceImpl(AccountMapper mapper) {
        this.mapper = mapper;
    }

    private String generateCard() {
        StringBuilder sb = new StringBuilder("6222");
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        while (sb.length() < 16) sb.append(rnd.nextInt(0, 10));
        return sb.toString();
    }

    @Override
    public Account login(String card, String password) {
        Account account = mapper.findByCard(card);
        if (account == null) {
            throw new RuntimeException("卡号不存在");
        }

        try {
            // 前端传来的是密文，先解密
            String decryptedPassword = PasswordUtils.decryptPassword(password);

            if (!account.getPassword().equals(decryptedPassword)) {
                throw new RuntimeException("密码错误");
            }

        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("加密配置错误: " + iae.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("密码解密失败");
        }

        return account;
    }

    @Override
    public Account register(Account account) {
        if (account.getCard() == null || account.getCard().isEmpty()) {
            String card;
            do {
                card = generateCard();  // 重新生成卡号
            } while (mapper.findByCard(card) != null);  // 确保卡号唯一
            account.setCard(card);
        }
        mapper.insert(account);
        return mapper.findByCard(account.getCard());
    }

    @Override
    public double deposit(DepositRequest req) {
        Account a = mapper.findByCard(req.getCard());
        if (a == null) throw new RuntimeException("账户不存在");

        if (req.getAmount() <= 0) throw new RuntimeException("存款金额必须大于 0");

        double newBalance = a.getBalance() + req.getAmount();
        mapper.updateBalance(req.getCard(), newBalance);

        return newBalance;
    }

    @Override
    public double withdraw(WithdrawRequest req) {
        Account a = mapper.findByCard(req.getCard());
        if (a == null) throw new RuntimeException("账户不存在");

        if (req.getAmount() <= 0) throw new RuntimeException("取款金额必须大于 0");

        if (a.getBalance() < req.getAmount()) throw new RuntimeException("余额不足");

        double newBalance = a.getBalance() - req.getAmount();
        mapper.updateBalance(req.getCard(), newBalance);

        return newBalance;
    }

    @Override
    public void transfer(TransferRequest req) {
        Account from = mapper.findByCard(req.getFromCard());
        Account to = mapper.findByCard(req.getToCard());

        if (from == null) throw new RuntimeException("转出账户不存在");
        if (to == null) throw new RuntimeException("收款账户不存在");

        if (req.getAmount() <= 0) throw new RuntimeException("金额必须大于 0");

        if (from.getBalance() < req.getAmount()) throw new RuntimeException("余额不足");

        double newFrom = from.getBalance() - req.getAmount();
        double newTo = to.getBalance() + req.getAmount();
        mapper.updateBalance(req.getFromCard(), newFrom);
        mapper.updateBalance(req.getToCard(), newTo);
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
