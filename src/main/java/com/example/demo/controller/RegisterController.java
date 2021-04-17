package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@Controller
public class RegisterController {
	
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping(value = "/register")
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("register");
        modelAndView.addObject("msg","用户注册");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/adminRegister")
	public ModelAndView adminRegister() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin-register");
        modelAndView.addObject("msg","管理员注册");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/doregister", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView register(@ModelAttribute User user, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		user.setRole("ROLE_USER");
		System.out.println(user.toString());
		String s = userService.register(user);
		System.out.println(user.toString());
		
		if (s.equals(new String("Sucess"))) {
	        modelAndView.setViewName("login");
	        modelAndView.addObject("msg","欢迎");
		}else {
	        modelAndView.setViewName("register");
	        modelAndView.addObject("msg",s);
		}
		System.out.println(s);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/doAdminRegister", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView adminRegister(@ModelAttribute User user, HttpSession session) {
		System.out.println("/doAdminRegister");
		ModelAndView modelAndView = new ModelAndView();
		user.setRole("ROLE_ADMIN");
		System.out.println(user.toString());
		String s = userService.register(user);
		System.out.println(user.toString());
		
		if (s.equals(new String("Sucess"))) {
	        modelAndView.setViewName("login");
	        modelAndView.addObject("msg","欢迎");
		}else {
	        modelAndView.setViewName("register");
	        modelAndView.addObject("msg",s);
		}
		System.out.println(s);
		
		return modelAndView;
	}

}
