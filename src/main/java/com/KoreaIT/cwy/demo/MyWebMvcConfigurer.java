package com.KoreaIT.cwy.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.KoreaIT.cwy.demo.interceptor.BeforeActionInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	// BeforeActionInterceptor 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	// /resource/common.css
	// 인터셉터 적용
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**")
				.excludePathPatterns("/error");
	}

}
