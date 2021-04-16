package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.example.demo.domain.User;

@Mapper
public interface UserMapper {
	
	@Insert("insert into user(uid,uname,password) values(#{uid},#{uname},#{password})")
	void create(User user);
	
	@Delete("delete from user where uid = #{uid}")
	void delete();
	
	@Update("update user set uname = #{uname},password= #{password} where uid = #{uid}  ")  
    void updateUser(User user);  
	
	@Select({"select uid,uname,password from user where uid = #{uid}"})
	@Results(id="userMap", value={
			@Result(column="uid", property="uid",jdbcType=JdbcType.VARCHAR, id=true),
		    @Result(column="uname", property="uname",jdbcType=JdbcType.VARCHAR),
		    @Result(column="password", property="password",jdbcType=JdbcType.VARCHAR),
	})
	User selectUserByUid(String uid);
	
	@Select({"select * from user"})
	@ResultMap("userMap")
	List<User> selectUsers();   
	
	

}
