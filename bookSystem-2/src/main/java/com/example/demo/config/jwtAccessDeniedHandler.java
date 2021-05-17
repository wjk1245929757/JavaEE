package com.example.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class jwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        System.out.println("用户访问没有授权资源");
        System.out.println(e.getMessage());
        JSONObject result = new JSONObject();
    	result.put("data", "Access is denied");
    	result.put("status", "401");
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e==null?"用户访问没有授权资源":e.getMessage());
    	//处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("application/json;charset=utf-8");
       //塞到HttpServletResponse中返回给前台
//        httpServletResponse.getWriter().write(new String("登录成功"));
        System.out.println(result.toJSONString());
        httpServletResponse.getWriter().write(result.toJSONString());
    }
}
