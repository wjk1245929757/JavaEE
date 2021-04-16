package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;

	@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();    
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
	    auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//				.antMatchers("/", "/home").permitAll()
//				.anyRequest().authenticated()
//				.and()
//			.formLogin()
//				.loginPage("/login")
//				.permitAll()
//				.and()
//			.logout()
//				.permitAll();
		
//		http
//			.authorizeRequests()
//				.antMatchers("/**").hasAnyRole("user")
//				.anyRequest() //任何请求
//				.authenticated()//都要进行身份认证
//				.and()
//			.formLogin()//表单登录
//				.loginPage("/login")//登录页面
//				.defaultSuccessUrl("/books")
//				.permitAll();//和登录相关的接口都不需要认证即可访问
		http
        	.formLogin()   //使用表单登录页面
        		.loginPage("/login")    //登录url
        		.loginProcessingUrl("/dologin")    //登录提交url
        		.defaultSuccessUrl("/books")
        		.and()
        	.authorizeRequests()
        		.antMatchers("/register", "/doregister", "/login", "/dologin").permitAll()
        		.anyRequest().authenticated()
        		.and()
        	.csrf().disable();

	}
	
	
}
