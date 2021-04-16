	package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;

@Controller
public class BookController {
	
	
	@Autowired
	private BookService bookService;
	
	
	//增
	@RequestMapping(value = {"/create"})
	public String create(HttpSession session) {
		session.setAttribute("msg", "添加");
		return "book-create";
	}
	
	@RequestMapping(value = {"/book"}, method = RequestMethod.POST)
	public String create(@ModelAttribute Book book, HttpSession session) {
		String s = bookService.create(book);
		if(s == "Sucess") {
			return "redirect:/books";
		}
		session.setAttribute("msg", s);
		return "book-create";
	}
	
	//删
	@RequestMapping(value = "/book/{bid}", method = RequestMethod.DELETE)
	public String delete(@PathVariable String bid, HttpSession session) {
		System.out.println("edit"+"  "+bid);
		bookService.delete(bid);
		return "redirect:/books";
	}
	
	//改
	@RequestMapping(value = {"/book/{bid}/edit"}, method = RequestMethod.POST)
	public String updatePage(@PathVariable String bid, Model model) {
		Book book = bookService.selectBookByBid(bid);
		model.addAttribute("book", book);
		System.out.println("edit"+book.toString());
		return "book-edit";
	}
	@RequestMapping(value = "/book/{bid}", method = RequestMethod.PUT)
	public String update(@PathVariable String bid, Book book) {
		System.out.println(book.toString());
		bookService.update(book);
		return "redirect:/books";
	}
	//查
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView selectBooks() {
		ModelAndView modelAndView = new ModelAndView("book-list");
		List<Book> bookList = bookService.selectBooks();
		String s = bookList.toString();
		System.out.println(s);
		modelAndView.addObject("books", bookList);
		return modelAndView;
		
	}
	@RequestMapping(value = "/books/{bid}", method = RequestMethod.GET)
	public String selectBookByBid(@PathVariable String bid, Model model) {
		Book book = bookService.selectBookByBid(bid);
		model.addAttribute("book", book);
		System.out.println("select"+book.toString());
		return "book-detail";
	}

}
