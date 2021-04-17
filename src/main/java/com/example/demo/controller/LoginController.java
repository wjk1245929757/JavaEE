package com.example.demo.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;


/*
 * 登录控制类
 * 功能：个人登录
 * 路由：
 * 			/login
 */

@Controller
public class LoginController {
	
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session) {
		System.out.println("/login");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken))
			return "redirect:/books";
		else {
			session.setAttribute("msg", "欢迎");
	        return "login";
		}
	}
	
	
//	@RequestMapping(value = "/login?error", method = RequestMethod.GET)
//	public String logout(HttpSession session) {
//
//		System.out.println("/login?error");
//		session.setAttribute("msg", "登录失败");
//		
//		return "login";
//	}
	
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String loginCheck(@RequestParam("username") String uid,
			@RequestParam("password") String password,
			HttpSession session) {
		System.out.println("/dologin");
		String s = userService.login(uid,password);
		System.out.println(s);
		if (s.equals(new String("Sucess"))) {
			User u = userService.getUserByUid(uid);
			System.out.println("new u:"+u.toString());
			if (u.getRole().equalsIgnoreCase(new String("ROLE_ADMIN"))) {
		        return "redirect:/admin/books";
			}
			return "redirect:/books";
		}else {
	        session.setAttribute("msg", s);
		}
		System.out.println(s);
		
		return "login";
	}
	
	

}
