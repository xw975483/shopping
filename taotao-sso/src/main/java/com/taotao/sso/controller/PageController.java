package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//跳转页面
@Controller
@RequestMapping("/page")
public class PageController {

	@RequestMapping("/register")
	public String showRegister(){
		return "register";
	}
}
