package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.ExtRateLimiter;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

@RestController
public class BookController {
	@Autowired
	private BookService bookService;
	
	//增
	@RequestMapping(value = {"/books"}, method = RequestMethod.POST)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String create(@RequestBody JSONObject jsonObject) {
        String name = jsonObject.getString("bname");
        String author = jsonObject.getString("author");
        String content = jsonObject.getString("content");
        
		Book book = new Book();
		book.setBname(name);
		book.setAuthor(author);
		book.setContent(content);
		bookService.createBook(book);
		System.out.println("create  "+book.toString());
		return book.getBid();
	}
	
	//删
	@RequestMapping(value = "/book/{bid}", method = RequestMethod.DELETE)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String delete(@PathVariable String bid, HttpSession session) {
		System.out.println("delete"+"  "+bid);
		bookService.deleteBook(bid);
		return "Success";
	}
	
	//改
	@RequestMapping(value = "/book/{bid}", method = RequestMethod.PUT)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String update(@PathVariable String bid, @RequestBody JSONObject jsonObject) {
        String name = jsonObject.getString("bname");
        String author = jsonObject.getString("author");
        String content = jsonObject.getString("content");
        
		Book book = bookService.selectBookByBid(bid);
		book.setBname(name);
		book.setAuthor(author);
		book.setContent(content);
		System.out.println("update:"+book.toString());
		bookService.updateBook(book);
		return "Success";
	}
	//改 上传文件
	@RequestMapping(value = "/book/{bid}/upload", method = RequestMethod.POST)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public String uploadFile(@PathVariable String bid, @RequestParam("file") MultipartFile file) {
		Book book = bookService.selectBookByBid(bid);
		bookService.uploadFile(file, book);
		System.out.println("uploadFile: "+book.toString());
		return "Success";
	}
	//改 删除文件
		@RequestMapping(value = "/book/{bid}/delete", method = RequestMethod.DELETE)
		@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
		public String deleteFile(@PathVariable String bid) {
			Book book = bookService.selectBookByBid(bid);
			bookService.deleteFile(book);
			System.out.println("deleteFile: "+book.toString());
			return "Success";
		}
	
	//查
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	@ResponseBody
	public List<Book> selectBooks() {
		return bookService.selectBooks();
		
	}
	@RequestMapping(value = "/books/{bid}", method = RequestMethod.GET)
	@ExtRateLimiter(permitsPerSecond = 2, timeout = 500)
	public Book selectBookByBid(@PathVariable String bid) {
		return bookService.selectBookByBid(bid);
	}
}
