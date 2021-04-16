package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Book;
import com.example.demo.mapper.BookMapper;

@Service
public class BookService {
	
	@Autowired
	private BookMapper bookMapper;
	
	private boolean isBidExisted(String bid) {
		if(bookMapper.selectBookByUid(bid) == null) {
			return false;
		}
		return true;
	}
	
	public String create(Book book) {
		if(isBidExisted(book.getBid())) {
			return "该序号已存在";
		}
		bookMapper.create(book);
		return "Sucess";
	}
	
	public void delete(String bid) {
		bookMapper.delete(bid);
	}
	
	public void update(Book book) {
		bookMapper.update(book);
	}
	
	public Book selectBookByBid(String bid) {
		return bookMapper.selectBookByUid(bid);
	}
	
	public List<Book> selectBooks(){
		return bookMapper.selectBooks();
	}

}
