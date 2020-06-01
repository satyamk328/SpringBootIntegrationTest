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
	public void When_FindById_ThenReturnSuccess() {
		Mockito.when(bookServiceImpl.findById(ArgumentMatchers.anyLong())).thenReturn(getBookResponse());
		ResponseEntity<RestResponse<Book>> responseEntity = bookController.findById(1L);
		Mockito.verify(bookServiceImpl, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(responseEntity.getBody().getData().getAuthorName(), getBookResponse().getAuthorName());
		assertEquals(responseEntity.getBody().getData().getDesc(), getBookResponse().getDesc());
		assertEquals(responseEntity.getBody().getData().getId(), getBookResponse().getId());
		assertEquals(responseEntity.getBody().getData().getName(), getBookResponse().getName());
		assertEquals(responseEntity.getBody().getData().getStatus(), getBookResponse().getStatus());
		assertEquals(responseEntity.getBody().getData().getLibrary().getAddress(), getBookResponse().getLibrary().getAddress());
		assertEquals(responseEntity.getBody().getData().getLibrary().getDesc(), getBookResponse().getLibrary().getDesc());
		assertEquals(responseEntity.getBody().getData().getLibrary().getGroupName(), getBookResponse().getLibrary().getGroupName());
		assertEquals(responseEntity.getBody().getData().getLibrary().getId(), getBookResponse().getLibrary().getId());
		assertEquals(responseEntity.getBody().getData().getLibrary().getName(), getBookResponse().getLibrary().getName());
	}

	@Test
	public void When_SearchBooks_ThenReturnSuccess() {
		Mockito.when(bookServiceImpl.findAll(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(getPageBookResponse());
		ResponseEntity<RestResponse<Page<Book>>> responseEntity = bookController.searchBook(1, 10, "", "");
		Mockito.verify(bookServiceImpl, Mockito.timeout(1)).findAll(ArgumentMatchers.anyInt(),
				ArgumentMatchers.anyInt(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getAuthorName(), getBooksResponse().get(0).getAuthorName());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getDesc(), getBooksResponse().get(0).getDesc());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getId(), getBooksResponse().get(0).getId());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getName(), getBooksResponse().get(0).getName());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getPrice(), getBooksResponse().get(0).getPrice());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getStatus(), getBooksResponse().get(0).getStatus());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getLibrary().getAddress(), getBooksResponse().get(0).getLibrary().getAddress());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getLibrary().getDesc(), getBooksResponse().get(0).getLibrary().getDesc());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getLibrary().getGroupName(), getBooksResponse().get(0).getLibrary().getGroupName());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getLibrary().getId(), getBooksResponse().get(0).getLibrary().getId());
		assertEquals(responseEntity.getBody().getData().getContent().get(0).getLibrary().getName(), getBooksResponse().get(0).getLibrary().getName());
	}
	
	
	@Test
	public void When_FindByLibraryId_ThenReturnSuccess() {
		Mockito.when(bookServiceImpl.findByLiberyId(ArgumentMatchers.anyLong())).thenReturn(getBooksResponse());
		ResponseEntity<RestResponse<List<Book>>> responseEntity = bookController.findByLibraryId(1L);
		Mockito.verify(bookServiceImpl, Mockito.timeout(1)).findByLiberyId(ArgumentMatchers.anyLong());
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
	public void When_AddBooks_ThenReturnSuccess() {
		Mockito.when(bookServiceImpl.save(ArgumentMatchers.any(Book.class))).thenReturn(getBookResponse());
		ResponseEntity<RestResponse<Book>> responseEntity = bookController.addBook(getBookResponse());
		Mockito.verify(bookServiceImpl, Mockito.timeout(1)).save(ArgumentMatchers.any(Book.class));
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(responseEntity.getBody().getData().getAuthorName(), getBookResponse().getAuthorName());
		assertEquals(responseEntity.getBody().getData().getDesc(), getBookResponse().getDesc());
		assertEquals(responseEntity.getBody().getData().getId(), getBookResponse().getId());
		assertEquals(responseEntity.getBody().getData().getName(), getBookResponse().getName());
		assertEquals(responseEntity.getBody().getData().getStatus(), getBookResponse().getStatus());
		assertEquals(responseEntity.getBody().getData().getLibrary().getAddress(), getBookResponse().getLibrary().getAddress());
		assertEquals(responseEntity.getBody().getData().getLibrary().getDesc(), getBookResponse().getLibrary().getDesc());
		assertEquals(responseEntity.getBody().getData().getLibrary().getGroupName(), getBookResponse().getLibrary().getGroupName());
		assertEquals(responseEntity.getBody().getData().getLibrary().getId(), getBookResponse().getLibrary().getId());
		assertEquals(responseEntity.getBody().getData().getLibrary().getName(), getBookResponse().getLibrary().getName());
	}
	
	@Test
	public void When_AddBooksByLibrary_ThenReturnSuccess() {
		Mockito.when(bookServiceImpl.save(ArgumentMatchers.any(Book.class), ArgumentMatchers.anyLong())).thenReturn(getBookResponse());
		ResponseEntity<RestResponse<Book>> responseEntity = bookController.addBook(1L, getBookResponse());
		Mockito.verify(bookServiceImpl, Mockito.timeout(1)).save(ArgumentMatchers.any(Book.class), ArgumentMatchers.anyLong());
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(responseEntity.getBody().getData().getAuthorName(), getBookResponse().getAuthorName());
		assertEquals(responseEntity.getBody().getData().getDesc(), getBookResponse().getDesc());
		assertEquals(responseEntity.getBody().getData().getId(), getBookResponse().getId());
		assertEquals(responseEntity.getBody().getData().getName(), getBookResponse().getName());
		assertEquals(responseEntity.getBody().getData().getStatus(), getBookResponse().getStatus());
		assertEquals(responseEntity.getBody().getData().getLibrary().getAddress(), getBookResponse().getLibrary().getAddress());
		assertEquals(responseEntity.getBody().getData().getLibrary().getDesc(), getBookResponse().getLibrary().getDesc());
		assertEquals(responseEntity.getBody().getData().getLibrary().getGroupName(), getBookResponse().getLibrary().getGroupName());
		assertEquals(responseEntity.getBody().getData().getLibrary().getId(), getBookResponse().getLibrary().getId());
		assertEquals(responseEntity.getBody().getData().getLibrary().getName(), getBookResponse().getLibrary().getName());
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
