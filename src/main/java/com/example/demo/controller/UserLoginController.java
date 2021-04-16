package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;


/*
 * 登录控制类
 * 功能：个人登录
 * 路由：
 * 			/login
 */

@Controller
public class UserLoginController {
	
	
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
	public String loginCheck(@Param("username")String uid, 
			@Param("password")String password,
			HttpSession session) {
		System.out.println("/dologin");
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (!(auth instanceof AnonymousAuthenticationToken))
//    		 return "redirect:/books";
//
//        else 
//	         return "login";
		User user = new User();
		user.setUid(uid);
		user.setPassword(password);
		System.out.println(user.toString());
		String s = userService.login(user);
		
		if (s.equals(new String("Sucess"))) {	
	        return "redirect:/books";
		}else {
	        session.setAttribute("msg", s);
		}
		System.out.println(s);
		
		return "login";
	}

}
