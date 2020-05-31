package com.test.restcontroller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.controller.BookController;
import com.test.model.Book;
import com.test.model.Library;
import com.test.service.impl.BookServiceImpl;
import com.text.request.model.RestResponse;

@RunWith(SpringRunner.class)
public class BookControllerTest {

	@Mock
	private BookServiceImpl bookServiceImpl;

	@InjectMocks
	private BookController bookController;

	@Test
	public void When_FindAllBooks_ThenReturnSuccess() {
		Mockito.when(bookServiceImpl.findAll()).thenReturn(getBooksResponse());
		ResponseEntity<RestResponse<?>> responseEntity = bookController.findAll();
		Mockito.verify(bookServiceImpl, Mockito.timeout(1)).findAll();
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
	}

	@Test
	public void When_FindId_ThenReturnSuccess() {
		Mockito.when(bookServiceImpl.findById(ArgumentMatchers.anyLong())).thenReturn(getBookResponse());
		ResponseEntity<RestResponse<?>> responseEntity = bookController.findById(1L);
		Mockito.verify(bookServiceImpl, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
	}

	@Test
	public void When_SearchBooks_ThenReturnSuccess() {
		Mockito.when(bookServiceImpl.findAll(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(getPageBookResponse());
		ResponseEntity<RestResponse<?>> responseEntity = bookController.searchBook(1, 10, "", "");
		Mockito.verify(bookServiceImpl, Mockito.timeout(1)).findAll(ArgumentMatchers.anyInt(),
				ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
	}

	private Page<Book> getPageBookResponse() {
		Page<Book> page = new PageImpl<Book>(getBooksResponse());
		return page;
	}

	private List<Book> getBooksResponse() {
		List<Book> list = new ArrayList<>();
		list.add(getBookResponse());
		return list;
	}

	private Book getBookResponse() {
		Book book = new Book();
		book.setId(1L);
		book.setName("Java");

		Library library = new Library();
		library.setName("Library");
		library.setId(1L);

		book.setLibrary(library);
		return book;
	}

}
