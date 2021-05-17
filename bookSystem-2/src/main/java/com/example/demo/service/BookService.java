package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import com.example.demo.util.FileService;
import com.example.demo.util.GetRandomStringService;

@Service
public class BookService {
	
	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private GetRandomStringService getRandomStringService;

	private static String folderPath = "public/";
	
	public void createBook(Book book) {
		book.setBid(getRandomStringService.getRandomString(6));
		bookMapper.create(book);
	}
//	@Insert("insert into book(bid,bname,content) values(#{bid},#{bname},#{content})")
//	void create(Book book);
//	
	public void deleteBook(String bid) {
		bookMapper.delete(bid);
	}
//	@Delete("delete from book where bid = #{bid}")
//	void delete(String bid);
//	
	public void updateBook(Book book) {
		bookMapper.update(book);
	}
	
	public void uploadFile(MultipartFile file, Book book) {
		book.setPath(folderPath + book.getBid()+file.getOriginalFilename());
		System.out.println(book.toString());
		fileService.save(file, book.getPath());
		bookMapper.uploadFile(book);
	}
	
	public void deleteFile(Book book) {
		fileService.deleteFile(book.getPath());
		bookMapper.deleteFile(book);
	}
	
//	@Update("update book set bname = #{bname}, content = #{content} where bid = #{bid}")  
//    void update(Book book);
//	
	public Book selectBookByBid(String bid) {
		return bookMapper.selectBookByBid(bid);
	}
//	@Select({"select bid,bname,content from book where bid = #{bid}"})
//	@Results(id="bookMap", value={
//			@Result(column="bid", property="bid",jdbcType=JdbcType.VARCHAR, id=true),
//		    @Result(column="bname", property="bname",jdbcType=JdbcType.VARCHAR),
//		    @Result(column="content", property="content",jdbcType=JdbcType.VARCHAR),
//	})
//	Book selectBookByUid(String bid);
//	
	public List<Book> selectBooks() {
		return bookMapper.selectBooks();
	}
//	@Select({"select * from book"})
//	@ResultMap("bookMap")
//	List<Book> selectBooks();   

}
