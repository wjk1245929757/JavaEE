package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;

@Service
public class UserService  implements UserDetailsService {
	
	@Autowired
	private UserMapper usermapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private boolean isUidExisted(String uid) {
		if(null == usermapper.selectUserByUid(uid)) {
			return false;
		}
		return true;
	}
	
	private boolean isUidMatchPassword(String uid,String password) {
		String psw = usermapper.selectUserByUid(uid).getPassword();
//		System.out.println(password);
//		System.out.println(psw);
//		System.out.println(passwordEncoder.matches(password, psw));
		if(passwordEncoder.matches(password, psw)) {
			return true;
		}
		return false;
	}
	
	public User getUserByUid(String uid) {
		return usermapper.selectUserByUid(uid);
	}
	
	public String login(User user) {
		if(isUidExisted(user.getUid())) {
			if( isUidMatchPassword( user.getUid(),user.getPassword() ) ) {
				return "Sucess";
			}
			return "密码错误";
		}
		return "账号不存在";
		
	}
	
	public String login(String uid, String password) {
		if(isUidExisted(uid)) {
			if( isUidMatchPassword( uid,password ) ) {
				return "Sucess";
			}
			return "密码错误";
		}
		return "账号不存在";
		
	}
	
	
	public String register(User user) {
		if(user.getUid() == null || user.getUname()==null || user.getPassword()==null) {
			return "输入信息不完整";
		}
		if(isUidExisted(user.getUid())) {
			return "账号已存在";
		}
		String psw =user.getPassword();
		String encodePsw = passwordEncoder.encode(psw);
		user.setPassword(encodePsw);
//		System.out.println(psw);
//		System.out.println(encodePsw);
		usermapper.create(user);
		return "Sucess";
	}

	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		User myUser = usermapper.selectUserByUid(uid);
//		System.out.println("uid:" + uid);
        if (myUser == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        String role = myUser.getRole();
        Collection<GrantedAuthority> authorities = new ArrayList<>();         
        authorities.add(new SimpleGrantedAuthority(role));
        org.springframework.security.core.userdetails.User user = 
        		new org.springframework.security.core.userdetails.User(myUser.getUid(),myUser.getPassword(),authorities);
        System.out.println("账号："+myUser.getUid()+"   "+myUser.getPassword()+"  "+user.getAuthorities());
//        System.out.println(myUser.getPassword());
        return user;

//        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        return new org.springframework.security.core.userdetails.User(user.getUid(),
//                user.getPassword(),
//                authorities);
	}
	
	
	

}
