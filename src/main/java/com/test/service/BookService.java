package com.test.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.test.model.Book;

public interface BookService {

	public Page<Book> findAll(Integer pageNo, Integer pageSize, String sortBy, String searchText) ;
	
	public List<Book> findAll();

	public Book findById(Long id);

	public Book save(Book book);
	
	public Book save(Book book, Long libraryId);

	public Book update(Book book);

	public void delete(Long id);
	
}
