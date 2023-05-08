package com.KoreaIT.cwy.demo.vo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.KoreaIT.cwy.demo.service.MemberService;
import com.KoreaIT.cwy.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	private Map<String, String> paramMap;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;

		this.session = req.getSession();

		paramMap = Ut.getParamMap(req);

		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;

		this.req.setAttribute("rq", this);

	}

	public void printHitoryBackJs(String msg) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHitoryBack("F-B", msg));
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}

	public String jsHitoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";

	}

	public String jsHitoryBack(String resultCode, String msg) {
		return Ut.jsHitoryBack(resultCode, msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}

	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();

		System.out.println(currentUri);
		System.out.println(queryString);

		if (queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}

		System.out.println(currentUri);
		return currentUri;
	}

	// Rq 객체 생성 유도
	// 삭제 x, BeforeActionInterceptor 에서 강제 호출
	public void initOnBeforeActionInterceptor() {

	}

	public boolean isNotLogined() {
		return !isLogined;
	}

	public void run() {
		System.out.println("===========================run A");
	}

	public void jsprintReplace(String msg, String replaceUri) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, replaceUri));
	}

	public String getLoginUri() {
		return "../member/login?afterLoginUri=" + getAfterLoginUri();
	}

	public String getLogoutUri() {
		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/usr/article/write":
			return "../member/doLogout?afterLogoutUri=" + "/";
		}

		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}

	public String getAfterLogoutUri() {
		return getEncodedCurrentUri();
	}

	private String getAfterLoginUri() {
//		로그인 후 접근 불가 페이지

		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/usr/member/login":
		case "/usr/member/join":
			return Ut.getEncodedUri(Ut.getAttr(paramMap, "afterLoginUri", ""));
		}

		return getEncodedCurrentUri();
	}

	public String getEncodedCurrentUri() {
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}
}
