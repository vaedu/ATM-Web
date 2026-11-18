package com.example.atm.service.impl;

import com.example.atm.Utils.SessionManager;
import com.example.atm.common.exception.*;
import com.example.atm.dto.LockResponse;
import com.example.atm.entity.Account;
import com.example.atm.mapper.AccountMapper;
import com.example.atm.mapper.AdminMapper;
import com.example.atm.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	private final AdminMapper adminMapper;
	private final AccountMapper accountMapper;
	private final SessionManager sessionManager;
	@Override
	public List<LockResponse> findAllAccounts() {
		log.info("查询所有账号");
		return adminMapper.findAllAccounts();
	}

	@Override
	public void unlock(String card) {
		log.info("解锁指定账号{}", card);
		Account account = accountMapper.findByCard(card);
		if (!account.getStatus().equals("LOCKED")) {
			throw new AccountNotLockedException("该账号未锁定");
		}
		adminMapper.unlock(card);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void limit(String card, BigDecimal max, BigDecimal min) {
		log.info("设置账号{}的额度限制", card);
		if (max.compareTo(min) <= 0) {
			throw new BalanceException("最大值必须大于最小值");
		}
		// 获取当前限额并调整（如果超出新范围）
		BigDecimal currentLimit = accountMapper.findByCard(card).getDailyLimit();
		if (currentLimit != null) {
			if (currentLimit.compareTo(max) > 0) {
				log.warn("当前限额{}超过新的最大值{}，强制调整为{}", currentLimit, max, max);
				accountMapper.changeLimit(card, max);
			} else if (currentLimit.compareTo(min) < 0) {
				log.warn("当前限额{}低于新的最小值{}，强制调整为{}", currentLimit, min, min);
				accountMapper.changeLimit(card, min);
			}
		} else {
			// 当前限额为空，设置为最小值
			log.info("当前限额为空，设置为最小值{}", min);
			accountMapper.changeLimit(card, min);
		}

		//  更新限额范围
		adminMapper.limit(card, max, min);
	}

	@Override
	public Account findLimitByCard(String card) {
		Account account = accountMapper.findByCard(card);
		if (account == null) {
			throw new AccountNotFoundException("账号不存在");
		}
		return account;
	}

	@Override
	public void lock(String card) {
		String status = accountMapper.findByCard(card).getStatus();
		if (status.equals("LOCKED")) {
			throw new AccountLockedException("该账号已锁定");
		}
		if (status.equals("TEST")) {
			throw new AccountLockedException("该账号为测试账号，不能锁定");
		}
		accountMapper.lockAccount(card);
		sessionManager.revoke(card);
	}

	@Override
	public void restore(String card) {
		String status = accountMapper.findByCard(card).getStatus();
		if (!status.equals("CANCEL")){
			throw new AccountNotCloseException("该账号未注销");
		}
		adminMapper.restore(card);
	}

}
