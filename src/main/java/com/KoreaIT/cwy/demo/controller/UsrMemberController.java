package com.KoreaIT.cwy.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.cwy.demo.service.MemberService;
import com.KoreaIT.cwy.demo.util.Ut;
import com.KoreaIT.cwy.demo.vo.Member;
import com.KoreaIT.cwy.demo.vo.ResultData;
import com.KoreaIT.cwy.demo.vo.Rq;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return ResultData.from("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return ResultData.from("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return ResultData.from("F-6", "이메일을 입력해주세요");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return (ResultData) joinRd;
		}

		Member member = memberService.getMemberById(joinRd.getData1());

		return ResultData.newData(joinRd, "member", member);
	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpSession httpSession) {
		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw) {

		if (rq.isLogined()) {
			return Ut.jsHitoryBack("F-5", "이미 로그인 상태입니다");
		}

		if (Ut.empty(loginId)) {
			return Ut.jsHitoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHitoryBack("F-2", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHitoryBack("F-3", Ut.f("%s는 존재하지 않는 아이디입니다", loginId));
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHitoryBack("F-4", Ut.f("비밀번호가 일치하지 않습니다"));
		}

		rq.login(member);

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getName()), "/");
	}

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout() {

		if (!rq.isLogined()) {
			return Ut.jsHitoryBack("F-1", "이미 로그아웃 상태입니다");
		}

		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", "/");
	}

	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {

		return "usr/member/myPage";
	}

	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {

		return "usr/member/checkPw";
	}

	@RequestMapping("/usr/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(String loginPw, String replaceUri) {
		System.out.println("========================================" + loginPw);
		System.out.println("========================================" + replaceUri);
		if (Ut.empty(loginPw)) {
			return rq.jsHitoryBackOnView("비밀번호 입력해");
		}

		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return rq.jsHitoryBack("", "비밀번호 틀림");
		}

		return rq.jsReplace("", replaceUri);
	}

	@RequestMapping("/usr/member/modify")
	public String showModify() {

		return "usr/member/modify";
	}

	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String loginPw, String name, String nickname, String cellphoneNum, String email) {

		if (Ut.empty(loginPw)) {
			loginPw = null;
		}
		if (Ut.empty(name)) {
			return rq.jsHitoryBackOnView("name 입력해");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHitoryBackOnView("nickname 입력해");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHitoryBackOnView("cellphoneNum 입력해");
		}
		if (Ut.empty(email)) {
			return rq.jsHitoryBackOnView("email 입력해");
		}

		ResultData modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum,
				email);

		return rq.jsReplace(modifyRd.getMsg(), "../member/myPage");
	}

}
