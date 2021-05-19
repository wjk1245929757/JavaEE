package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.config.ExtRateLimiter;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.CheckCodeUtils;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CheckCodeUtils checkCodeUtils;
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String Register(@RequestBody JSONObject jsonObject) {
		
		System.out.println("register");
		String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        String telephone = jsonObject.getString("telephone");
        
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setTelephone(telephone);
		System.out.println(user.toString());
		
		String temp =userService.register(user);
		System.out.println(temp);
		
		return temp;
	}
	
	@RequestMapping(value = "/sendCheckCode", method = RequestMethod.POST)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String SendCheckCode(@RequestBody JSONObject jsonObject) {
		String email = jsonObject.getString("email");
		return checkCodeUtils.sendCheckCode(email);
	}
	
	@RequestMapping(value = "/getrole", method = RequestMethod.GET)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String getRole() {
		System.out.println(userService.selectUserByEmail( (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getRole());
		return userService.selectUserByEmail( (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal() ).getRole();
	}
	
	@RequestMapping(value = "/user/user", method = RequestMethod.PUT)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String updateUser(@RequestBody JSONObject jsonObject) {
//		String email = jsonObject.getString("email");
		String password = jsonObject.getString("password");
        String role = jsonObject.getString("role");
        System.out.println("/user/user"+"   put: "+"password: "+password+"  role:"+role);
        
		User user = userService.selectUserByEmail( (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
//		System.out.println(user.getPassword());
		user.setPassword(userService.encodePassword(password));
		if(!role.equals(new String("ROLE_ADMIN"))) {
			role = "ROLE_USER";
		}
		user.setRole(role);
		System.out.println(user.toString());
		userService.updateUser(user);
		return "Success";
	}
	
	@RequestMapping(value = "/user/user", method = RequestMethod.GET)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public User getUser() {
//		String email = jsonObject.getString("email");
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return userService.selectUserByEmail( (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal() );
	}
	
	@RequestMapping(value = "/user/user", method = RequestMethod.DELETE)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String deleteUser() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("delete"+"  "+email);
		userService.deleteUser(email);
		return "Success";
	}
}
