package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;

@Controller
public class MainPageController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/")
	public String IndexPage(){
        return "redirect:/login";
    }
	
	@GetMapping("/index")
	public ModelAndView HomePage(){
        ModelAndView modelAndView = new ModelAndView();
        List<Book> bookList = bookService.selectBooks();
        modelAndView.addObject("books",bookList);
        modelAndView.setViewName("index");
        return modelAndView;
    }
	

}

