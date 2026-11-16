package com.example.atm.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.atm.Utils.RedisConstants.LOGIN_KEY;
import static com.example.atm.Utils.RedisConstants.SESSION_IDLE_TTL;

/**
 * 登录会话管理：Redis 中保存"每个卡号当前有效的 jti"，用于
 * <ul>
 *   <li>校验 token 是否仍然有效（被踢、退出登录后立即失效）；</li>
 *   <li>滑动续期：用户有操作就重置空闲超时。</li>
 * </ul>
 * key = LOGIN_KEY + 卡号，value = jti，TTL = 空闲超时。
 * 同一卡号同一时间只保留一个会话，后登录的会覆盖先登录的。
 */
@SuppressWarnings("BooleanMethodIsAlwaysInverted")
@Component
@RequiredArgsConstructor
public class SessionManager {

	private final StringRedisTemplate stringRedisTemplate;

	/**
	 * 原子操作：key 当前保存的 jti 与传入的一致，才重置过期时间并返回 1，否则返回 0。
	 * 用 Lua 脚本是为了"比对"和"续期"之间不会被别的请求打断，
	 * 并且被删除（被踢）的 key 不会因为续期而复活。
	 */
	private static final DefaultRedisScript<Long> VERIFY_AND_RENEW = new DefaultRedisScript<>(
			"if redis.call('get', KEYS[1]) == ARGV[1] then "
					+ "redis.call('expire', KEYS[1], ARGV[2]) "
					+ "return 1 "
					+ "else return 0 end",
			Long.class);

	/**
	 * 登录成功后创建会话，覆盖该卡号之前的会话（旧设备下一次请求会收到 401）。
	 *
	 * @param card 卡号
	 * @param jti  本次登录令牌的唯一标识
	 */
	public void create(String card, String jti) {
		stringRedisTemplate.opsForValue().set(LOGIN_KEY + card, jti, SESSION_IDLE_TTL);
	}

	/**
	 * 校验会话是否有效。
	 *
	 * @param card  卡号（取自 已验签通过的 token）
	 * @param jti   token 里的 jti
	 * @param renew true：校验通过后重置空闲超时（普通请求）；false：只校验不续期（心跳请求）
	 * @return 会话有效返回 true
	 */
	public boolean validate(String card, String jti, boolean renew) {
		if (card == null || jti == null) {
			return false;
		}
		String key = LOGIN_KEY + card;
		if (renew) {
			Long ok = stringRedisTemplate.execute(
					VERIFY_AND_RENEW,
					List.of(key),
					jti,
					String.valueOf(SESSION_IDLE_TTL.toSeconds()));
			return Long.valueOf(1L).equals(ok);
		}
		return jti.equals(stringRedisTemplate.opsForValue().get(key));
	}

	/**
	 * 作废某个卡号的会话（踢下线、锁定、退出登录时调用）。
	 *
	 * @param card 卡号
	 */
	public void revoke(String card) {
		stringRedisTemplate.delete(LOGIN_KEY + card);
	}
}
