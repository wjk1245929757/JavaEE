package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

@Service
public class UserService  implements UserDetailsService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	public　

	public void createUser(User user) {
		userMapper.create(user);
	}
//	@Insert("insert into user(email,name,password,telephone,role) values(#{email},#{name},#{password},#{telephone},#{role})")
//	void create(User user);
//	
	
	public void deleteUser(String email) {
		userMapper.delete(email);
	}
//	@Delete("delete from user where email = #{email}")
//	void delete();
//	
	
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}
//	@Update("update user set name = #{name},password= #{password},telephone= #{telephone} where email = #{email}  ")  
//    void updateUser(User user);  
//	
	
	public User selectUserByEmail(String email) {
		return userMapper.selectUserByEmail(email);
	}
//	@Select({"select email,name,password,telephone,role from user where email = #{email}"})
//	@Results(id="userMap", value={
//			@Result(column="email", property="email",jdbcType=JdbcType.VARCHAR, id=true),
//		    @Result(column="name", property="name",jdbcType=JdbcType.VARCHAR),
//		    @Result(column="password", property="password",jdbcType=JdbcType.VARCHAR),
//		    @Result(column="telephone", property="telephone",jdbcType=JdbcType.VARCHAR),
//		    @Result(column="role", property="role",jdbcType=JdbcType.VARCHAR),
//	})
//	User selectUserByEmail(String email);
//	
	public List<User> selectUsers() {
		return userMapper.selectUsers();
	}
//	@Select({"select * from user"})
//	@ResultMap("userMap")
//	List<User> selectUsers(); 
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User myUser = userMapper.selectUserByEmail(email);
//		System.out.println("loadUserByUsername:" + email);
        if (myUser == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        String role = myUser.getRole();
        Collection<GrantedAuthority> authorities = new ArrayList<>();         
        authorities.add(new SimpleGrantedAuthority(role));
        org.springframework.security.core.userdetails.User user = 
        		new org.springframework.security.core.userdetails.User(myUser.getEmail(),myUser.getPassword(),authorities);
        System.out.println("UserDetailsService work!   账号："+myUser.getEmail()+"   加密密码"+myUser.getPassword()+"  权限"+user.getAuthorities());
//        System.out.println(myUser.getPassword());
        return user;

//        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        return new org.springframework.security.core.userdetails.User(user.getUid(),
//                user.getPassword(),
//                authorities);
	}

	public String register(User user) {
		if(isUserExisted(user.getEmail())) {
			return "账号已存在";
		}
		user.setRole("ROLE_USER");
		String psw =user.getPassword();
		String encodePsw = passwordEncoder.encode(psw);
		user.setPassword(encodePsw);
		userMapper.create(user);
		return "注册成功";
	}
	
	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}
	
	public boolean isUserExisted(String email) {
		if(userMapper.selectUserByEmail(email) == null) {
			return false;
		}
		return true;
	}
	
	public boolean isPasswordMatchEmail(String email,String password) {
		String psw = userMapper.selectUserByEmail(email).getPassword();
		if(passwordEncoder.matches(password, psw)) {
			return true;
		}
		return false;
	}

}
