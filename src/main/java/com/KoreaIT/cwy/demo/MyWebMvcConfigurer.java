package com.KoreaIT.cwy.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.KoreaIT.cwy.demo.interceptor.BeforeActionInterceptor;
import com.KoreaIT.cwy.demo.interceptor.NeedAdminInterceptor;
import com.KoreaIT.cwy.demo.interceptor.NeedLoginInterceptor;
import com.KoreaIT.cwy.demo.interceptor.NeedLogoutInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

	@Value("${custom.genFileDirPath}")
	private String genFileDirPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/gen/**").addResourceLocations("file:///" + genFileDirPath + "/")
				.setCachePeriod(20);
	}

	// BeforeActionInterceptor 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	// NeedLoginInterceptor 불러오기
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;

	// NeedLogoutInterceptor 불러오기
	@Autowired
	NeedLogoutInterceptor needLogoutInterceptor;

	// NeedLogoutInterceptor 불러오기
	@Autowired
	NeedAdminInterceptor needAdminInterceptor;

	// /resource/common.css
	// 인터셉터 적용
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		InterceptorRegistration ir;

		ir = registry.addInterceptor(beforeActionInterceptor);
		ir.addPathPatterns("/**");
		ir.addPathPatterns("/favicon.ico");
		ir.excludePathPatterns("/resource/**");
		ir.excludePathPatterns("/error");

		ir = registry.addInterceptor(needLoginInterceptor);
		ir.addPathPatterns("/usr/article/write");
		ir.addPathPatterns("/usr/article/doWrite");
		ir.addPathPatterns("/usr/article/modify");
		ir.addPathPatterns("/usr/article/doModify");
		ir.addPathPatterns("/usr/article/doDelete");

		ir.addPathPatterns("/usr/member/myPage");
		ir.addPathPatterns("/usr/member/checkPw");
		ir.addPathPatterns("/usr/member/doCheckPw");
		ir.addPathPatterns("/usr/member/modify");
		ir.addPathPatterns("/usr/member/doModify");

		ir.addPathPatterns("/usr/reply/doWrite");
		ir.addPathPatterns("/usr/reply/modify");
		ir.addPathPatterns("/usr/reply/doModify");
		ir.addPathPatterns("/usr/reply/doDelete");

		ir.addPathPatterns("/usr/reactionPoint/doGoodReaction");
		ir.addPathPatterns("/usr/reactionPoint/doBadReaction");
		ir.addPathPatterns("/usr/reactionPoint/doCancelGoodReaction");
		ir.addPathPatterns("/usr/reactionPoint/doCancelBadReaction");

		ir.addPathPatterns("/adm/**");
		ir.addPathPatterns("/adm/member/login");
		ir.addPathPatterns("/adm/member/doLogin");
		ir.addPathPatterns("/adm/member/findLoginId");
		ir.addPathPatterns("/adm/member/doFindLoginId");
		ir.addPathPatterns("/adm/member/findLoginPw");
		ir.addPathPatterns("/adm/member/doFindLoginPw");

		ir = registry.addInterceptor(needLogoutInterceptor);
		ir.addPathPatterns("/usr/member/login");
		ir.addPathPatterns("/usr/member/doLogin");
		ir.addPathPatterns("/usr/member/getLoginIdDup");
		ir.addPathPatterns("/usr/member/join");
		ir.addPathPatterns("/usr/member/doJoin");
		ir.addPathPatterns("/usr/member/findLoginId");
		ir.addPathPatterns("/usr/member/doFindLoginId");
		ir.addPathPatterns("/usr/member/findLoginPw");
		ir.addPathPatterns("/usr/member/doFindLoginPw");

		ir = registry.addInterceptor(needAdminInterceptor);
		ir.addPathPatterns("/adm/**");
		ir.addPathPatterns("/adm/member/login");
		ir.addPathPatterns("/adm/member/doLogin");
		ir.addPathPatterns("/adm/member/findLoginId");
		ir.addPathPatterns("/adm/member/doFindLoginId");
		ir.addPathPatterns("/adm/member/findLoginPw");
		ir.addPathPatterns("/adm/member/doFindLoginPw");
	}

}
