package com.example.demo.domain;

public class Book {
	private String bid;
	private String bname;
	private String content;
	
	public Book() {
		super();
	}
	public Book(String bid, String bname, String content) {
		this.bid = bid;
		this.bname = bname;
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", content=" + content + "]";
	}
	

}
