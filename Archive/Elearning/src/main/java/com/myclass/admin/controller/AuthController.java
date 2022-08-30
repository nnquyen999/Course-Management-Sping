package com.myclass.admin.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myclass.dto.LoginDto;
import com.myclass.dto.SessionDto;
import com.myclass.service.AuthService;

@Controller
@RequestMapping("")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@Value("${path.login}")
	private String loginPath;
	
	@Value("${adress.home}")
	private String homepage;
	
	@RequestMapping(value="login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("loginDto", new LoginDto());
		return "login/index";
	}
	@RequestMapping(value="login", method = RequestMethod.POST)
	public String login(@ModelAttribute LoginDto loginDto, Model model, HttpServletRequest req) {
		SessionDto sessionDto = authService.login(loginDto);
		if(sessionDto == null) {
			model.addAttribute("message", "Tên hoặc mật khẩu không chính xác");
			return loginPath;
		}
		HttpSession session = req.getSession();
		session.setAttribute("sessionDto", sessionDto);
		session.setMaxInactiveInterval(1200);
		return "redirect:" + homepage;
	}
	@RequestMapping(value="logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:" + "/login";
	}
}
