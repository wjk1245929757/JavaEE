package com.example.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.UserService;


@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Autowired
	private UserService userService;
 
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        
    	String email = httpServletRequest.getParameter("email");
    	String msg = "登陆失败";
    	if(!userService.isUserExisted(email)) {
    		msg = "账号不存在";
    	}else {
    		msg = "密码错误";
    	}
//    	String password = httpServletRequest.getParameter("password");
//    	System.out.println(email+"   "+password);

    	JSONObject result = new JSONObject();
    	result.put("data", msg);
    	
    	
       //处理编码方式，防止中文乱码的情况
//    	System.out.println("CustomizeAuthenticationFailureHandler:"+result.toJSONString()+"   correct psw:"+userService.selectUserByEmail(email).getPassword());
        httpServletResponse.setContentType("application/json;charset=utf-8");
       //塞到HttpServletResponse中返回给前台
//        httpServletResponse.getWriter().write(new String("登录成功"));
        httpServletResponse.getWriter().write(result.toJSONString());
    }
}
