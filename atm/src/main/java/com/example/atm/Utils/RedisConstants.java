package com.example.atm.Utils;

import java.time.Duration;

public class RedisConstants {
	// 失败次数
	public static final String FAIL_KEY = "login:fail:card:";
	// 是否锁定
	public static final String LOCK_KEY = "login:lock:card:";
	// 当前登陆卡号
	static final String LOGIN_KEY = "login:card:";
	// 会话空闲超时：用户每发一次普通请求就重新计时，超过这段时间没有操作就视为掉线
	static final Duration SESSION_IDLE_TTL = Duration.ofMinutes(15);
	// 心跳接口路径：只校验会话是否还在，不续期（必须和 AccountController 里的 /survival 一致）
	public static final String HEARTBEAT_PATH = "/api/atm/survival";

}
