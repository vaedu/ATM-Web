package com.example.atm.config;

import com.example.atm.interceptor.AdminTokenInterceptor;
import com.example.atm.interceptor.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
	private final TokenInterceptor tokenInterceptor;
	private final AdminTokenInterceptor adminTokenInterceptor;


	private static final String[] ADMIN_PATHS = {"/api/atm/admin/**", "/api/admin/**"};

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tokenInterceptor)
				.addPathPatterns("/api/**")
				.excludePathPatterns("/api/atm/login", "/api/atm/register")
				// 管理接口只由管理端拦截器负责，避免用户端拦截器重复校验和续期
				.excludePathPatterns(ADMIN_PATHS);
		registry.addInterceptor(adminTokenInterceptor)
				.addPathPatterns(ADMIN_PATHS);
	}
}
