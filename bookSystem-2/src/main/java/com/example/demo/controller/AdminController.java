package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.config.ExtRateLimiter;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private UserService userService;
	
	//查
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public List<User> selectUsers(){
		System.out.println("/users get请求");
		return userService.selectUsers();
	}
	
	//增
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String createUser(@RequestBody JSONObject jsonObject){
		String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        String role = jsonObject.getString("role");
		User user = new User();
		user.setEmail(email);
		user.setPassword(userService.encodePassword(password));
		user.setRole(role);
		System.out.println("/user post  "+user.toString());
		userService.createUser(user);
		return "Success";
	}
	
	//删
	@RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String delete(@PathVariable String email, HttpSession session) {
		System.out.println("delete"+"  "+email);
		userService.deleteUser(email);
		return "Success";
	}
	
	//改
	@RequestMapping(value = "/user/{email}", method = RequestMethod.PUT)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String update(@PathVariable String email, @RequestBody JSONObject jsonObject) {
        String password = jsonObject.getString("password");
        String role = jsonObject.getString("role");
        System.out.println("/user/"+email+"   put: "+"password: "+password+"  role:"+role);
		User user = userService.selectUserByEmail(email);
		if(user == null) {
			return "Error";
		}
		user.setPassword(userService.encodePassword(password));
		if(!role.equals(new String("ROLE_ADMIN"))) {
			role = "ROLE_USER";
		}
		user.setRole(role);
		System.out.println("/user/"+email+"   put: "+user.toString());
		userService.updateUser(user);
		return "Success";
	}

}
