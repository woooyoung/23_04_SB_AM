package com.KoreaIT.cwy.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class UsrAjaxTestController {

	@RequestMapping("usr/home/plus")
	String showTestPage() {
		return "usr/home/AjaxTest";
	}

	@RequestMapping("usr/home/doPlus")
	@ResponseBody
	int doPlus(int num1, int num2) {
		return num1 + num2;
	}
}
