package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.util.GetAbsolutePathService;
@Configuration
//public class WebMvcConfiger extends WebMvcConfigurerAdapter {
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private GetAbsolutePathService getABPathService;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String path = getABPathService.getAbsolutePath();
		registry.addResourceHandler("/public/**")
			.addResourceLocations("classpath:public/")
			.addResourceLocations("file:" + path + "public/");
		registry.addResourceHandler("/static/**")
			.addResourceLocations("classpath:static/")
			.addResourceLocations("file:" + path + "static/");
  }
	
	@Bean
	public WebMvcConfigurer corsConfigurer(){
	      return new WebMvcConfigurer() {
	    	  @Override
	    	  public void addCorsMappings(CorsRegistry registry) {
	    		  registry.addMapping("/**").
	              allowedOrigins("*"). //允许跨域的域名，可以用*表示允许任何域名
	              allowedMethods("*"). //允许任何方法（post、get等）
	              allowedHeaders("*");
	             }
	      };
	 }
	
	@Autowired
	private RateLimiterInceptor rateLimiterInceptor;
	 
	  @Override
	  public void addInterceptors(InterceptorRegistry registry) {
	    // 获取http请求拦截器
	    registry.addInterceptor(rateLimiterInceptor).addPathPatterns("/*");
	  }
}

