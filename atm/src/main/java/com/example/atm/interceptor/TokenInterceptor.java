package com.example.atm.interceptor;


import com.example.atm.Utils.CurrentUser;
import com.example.atm.Utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import java.io.IOException;

import static com.example.atm.Utils.RedisConstants.LOGIN_KEY;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {
	private final JwtUtils jwtUtils;
	private final StringRedisTemplate stringRedisTemplate;
	/**
	 * 在请求处理之前进行拦截，验证请求的令牌。
	 *
	 * @param request 当前HTTP请求对象
	 * @param response 当前HTTP响应对象
	 * @param handler 被调用的处理器对象
	 * @return 如果请求被允许继续处理则返回true，否则返回false
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,
	                         @NonNull Object handler) throws IOException {
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) return true;   // 跨域预检不带 token

		String token = request.getHeader("token");
		if (token == null || token.isEmpty()) {
			return reject(response, "未登录");
		}
		try {
			// 解析token获取用户信息
			Claims claims = jwtUtils.parseUserOrAdminToken(token);
			String card = claims.get("card", String.class);
			String status = claims.get("status",String.class);
			request.setAttribute(CurrentUser.ATTR, card);
			// 非测试用户需验证Redis中的会话一致性
			if (!"TEST".equals(status)){
				String jti = claims.get("jti", String.class);
				String jti_R = stringRedisTemplate.opsForValue().get(LOGIN_KEY + card);
				if (jti_R == null || !jti_R.equals(jti)) {
					return reject(response, "登录已失效，请重新登录");
				}
			}
		} catch (JwtException e) {
			return reject(response, "登录已失效，请重新登录");
		}
		return true;
	}

	private boolean reject(HttpServletResponse response, String msg) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("{\"success\":false,\"message\":\"" + msg + "\"}");
		return false;
	}

}