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

import com.example.demo.domain.Book;

@Mapper
public interface BookMapper {
	@Insert("insert into book(bid,bname,content) values(#{bid},#{bname},#{content})")
	void create(Book book);
	
	@Delete("delete from book where bid = #{bid}")
	void delete(String bid);
	
	@Update("update book set bname = #{bname}, content = #{content} where bid = #{bid}")  
    void update(Book book);
	
	@Select({"select bid,bname,content from book where bid = #{bid}"})
	@Results(id="bookMap", value={
			@Result(column="bid", property="bid",jdbcType=JdbcType.VARCHAR, id=true),
		    @Result(column="bname", property="bname",jdbcType=JdbcType.VARCHAR),
		    @Result(column="content", property="content",jdbcType=JdbcType.VARCHAR),
	})
	Book selectBookByUid(String bid);
	
	@Select({"select * from book"})
	@ResultMap("bookMap")
	List<Book> selectBooks();   
	
	

}
