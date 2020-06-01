package com.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.test.exception.BusinessException;
import com.test.model.Book;
import com.test.model.Library;
import com.test.repository.BookRepository;
import com.test.service.BookService;
import com.test.service.LibraryService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private LibraryService libRepo;

	@Override
	public Page<Book> findAll(Integer pageNo, Integer pageSize, String sortBy, String searchText) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

		Page<Book> pages = StringUtils.isEmpty(searchText) ? bookRepo.findAll(pageable)
				: bookRepo.findByfullTextSearch(searchText.toLowerCase(), pageable);
		return pages;
	}
	
	@Override
	public List<Book> findAll() {
		return bookRepo.findAll();
	}

	@Override
	public Book findById(Long id) {
		Book book = bookRepo.findById(id).orElse(null);
		if (book == null)
			throw new BusinessException("Book Id is invalid. Please enter valid id");
		return book;
	}

	@Override
	@Transactional
	public Book save(Book book, Long libraryId) {
		Library library = libRepo.findById(libraryId);
		book.setLibrary(library);
		return bookRepo.save(book);
	}
	
	@Override
	@Transactional
	public Book save(Book book) {
		return bookRepo.save(book);
	}

	@Override
	@Transactional
	public Book update(Book book) {
		findById(book.getId());
		Library library = libRepo.findById(book.getLibrary().getId());
		book.setLibrary(library);
		return bookRepo.save(book);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		findById(id);
		bookRepo.deleteById(id);
	}

}
