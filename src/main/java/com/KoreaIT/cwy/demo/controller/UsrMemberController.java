package com.KoreaIT.cwy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.service.MemberService;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		memberService.doJoin(loginId, loginPw, name, nickname, cellphoneNum, email);

		return "가입!";
	}

//	@RequestMapping("/usr/article/doWrite")
//	@ResponseBody
//	public Article doWrite(String title, String body) {
//		int id = articleService.writeArticle(title, body);
//
//		Article article = articleService.getArticle(id);
//
//		return article;
//	}

}
