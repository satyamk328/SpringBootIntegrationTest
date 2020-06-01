package com.test.restcontroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.controller.BookController;
import com.test.model.Book;
import com.test.model.Library;
import com.test.request.model.RestResponse;
import com.test.service.impl.BookServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookControllerTest {

	@Mock
	private BookServiceImpl bookServiceImpl;

	@InjectMocks
	private BookController bookController;

	@Test
	public void When_FindAllBooks_ThenReturnSuccess() {
		Mockito.when(bookServiceImpl.findAll()).thenReturn(getBooksResponse());
		ResponseEntity<RestResponse<List<Book>>> responseEntity = bookController.findAll();
		Mockito.verify(bookServiceImpl, Mockito.timeout(1)).findAll();
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(responseEntity.getBody().getData().get(0).getAuthorName(), getBooksResponse().get(0).getAuthorName());
		assertEquals(responseEntity.getBody().getData().get(0).getDesc(), getBooksResponse().get(0).getDesc());
		assertEquals(responseEntity.getBody().getData().get(0).getId(), getBooksResponse().get(0).getId());
		assertEquals(responseEntity.getBody().getData().get(0).getName(), getBooksResponse().get(0).getName());
		assertEquals(responseEntity.getBody().getData().get(0).getPrice(), getBooksResponse().get(0).getPrice());
		assertEquals(responseEntity.getBody().getData().get(0).getStatus(), getBooksResponse().get(0).getStatus());
		assertEquals(responseEntity.getBody().getData().get(0).getLibrary().getAddress(), getBooksResponse().get(0).getLibrary().getAddress());
		assertEquals(responseEntity.getBody().getData().get(0).getLibrary().getDesc(), getBooksResponse().get(0).getLibrary().getDesc());
		assertEquals(responseEntity.getBody().getData().get(0).getLibrary().getGroupName(), getBooksResponse().get(0).getLibrary().getGroupName());
		assertEquals(responseEntity.getBody().getData().get(0).getLibrary().getId(), getBooksResponse().get(0).getLibrary().getId());
		assertEquals(responseEntity.getBody().getData().get(0).getLibrary().getName(), getBooksResponse().get(0).getLibrary().getName());
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
		book.setAuthorName("KP Thakur");
		book.setDesc("Created sample request");
		book.setStatus(true);
		book.setPrice(new BigDecimal(120));

		Library library = new Library();
		library.setId(1L);
		library.setName("Library");
		library.setDesc("Created sample request");
		library.setGroupName("test");
		library.setRanking(12L);
		library.setAddress("Lodhi Road");
		library.setStatus(true);

		book.setLibrary(library);
		return book;
	}

}
