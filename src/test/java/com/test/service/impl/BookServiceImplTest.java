package com.test.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.exception.BusinessException;
import com.test.model.Book;
import com.test.model.Library;
import com.test.repository.BookRepository;
import com.test.service.LibraryService;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceImplTest {

	@Mock
	private BookRepository bookRepository;
	
	@Mock
	private LibraryService libRepo;

	@InjectMocks
	private BookServiceImpl bookServiceImpl;

	@Test
	public void When_SearchAllBooksWithSearchTextIsNull_ThenReturnSuccess() {
		Mockito.when(bookRepository.findAll(ArgumentMatchers.any(Pageable.class)))
				.thenReturn(getPageBookResponse());
		Page<Book> books = bookServiceImpl.findAll(1, 10, "name", null);
		Mockito.verify(bookRepository, Mockito.timeout(1))
				.findAll(ArgumentMatchers.any(Pageable.class));
		assertNotNull(books);
		assertNotNull(books.getContent());
		assertEquals(books.getNumber(), 0);	
		assertEquals(books.getTotalElements(), 1);
		assertEquals(books.getContent().get(0).getId(), getPageBookResponse().getContent().get(0).getId());
		assertEquals(books.getContent().get(0).getName(), getPageBookResponse().getContent().get(0).getName());
	}

	@Test
	public void When_SearchAllBooks_ThenReturnSuccess() {
		Mockito.when(bookRepository.findByfullTextSearch(ArgumentMatchers.anyString(),
				ArgumentMatchers.any(Pageable.class)))
				.thenReturn(getPageBookResponse());
		Page<Book> books = bookServiceImpl.findAll(1, 10, "name", "test");
		Mockito.verify(bookRepository, Mockito.timeout(1))
		.findByfullTextSearch(ArgumentMatchers.anyString(),
				ArgumentMatchers.any(Pageable.class));
		assertNotNull(books);
		assertNotNull(books.getContent());
		assertEquals(books.getNumber(), 0);	
		assertEquals(books.getTotalElements(), 1);
		assertEquals(books.getContent().get(0).getId(), getPageBookResponse().getContent().get(0).getId());
		assertEquals(books.getContent().get(0).getName(), getPageBookResponse().getContent().get(0).getName());
	}

	@Test
	public void When_FindAllBooks_ThenReturnSuccess() {
		Mockito.when(bookRepository.findAll()).thenReturn(getBooksResponse());
		List<Book> books = bookServiceImpl.findAll();
		Mockito.verify(bookRepository, Mockito.timeout(1)).findAll();
		assertNotNull(books);
		assertEquals(books.get(0).getId(), getBooksResponse().get(0).getId());
		assertEquals(books.get(0).getName(), getBooksResponse().get(0).getName());
	}

	@Test
	public void When_FindById_ThenReturnSuccess() {
		Mockito.when(bookRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(getBookResponse()));
		Book book = bookServiceImpl.findById(1L);
		Mockito.verify(bookRepository, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
		assertNotNull(book);
		assertEquals(book.getId(), getBookResponse().getId());
		assertEquals(book.getName(), getBookResponse().getName());
	}

	@Test(expected = BusinessException.class)
	public void When_FindByInvalidId_ThenReturnSuccess() {
		bookServiceImpl.findById(1L);
		Mockito.verify(bookRepository, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
	}

	@Test
	public void When_AddBooks_ThenReturnSuccess() {
		Mockito.when(bookRepository.save(ArgumentMatchers.any())).thenReturn(getBookResponse());
		Book book = bookServiceImpl.save(new Book());
		Mockito.verify(bookRepository, Mockito.timeout(1)).save(ArgumentMatchers.any());
		assertNotNull(book);
		assertEquals(book.getId(), getBookResponse().getId());
		assertEquals(book.getName(), getBookResponse().getName());
		assertNotNull(book.getLibrary());
	}

	@Test
	public void When_UpadteBooks_ThenReturnSuccess() {
		Mockito.when( libRepo.findById(ArgumentMatchers.anyLong())).thenReturn(new Library());
		Mockito.when(bookRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(getBookResponse()));
		Mockito.when(bookRepository.save(ArgumentMatchers.any())).thenReturn(getBookResponse());
		Book book = bookServiceImpl.update(getBookResponse());
		Mockito.verify(bookRepository, Mockito.timeout(1)).save(ArgumentMatchers.any());
		Mockito.verify(bookRepository, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
		Mockito.verify(libRepo, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
		assertNotNull(book);
		assertEquals(book.getId(), getBookResponse().getId());
		assertEquals(book.getName(), getBookResponse().getName());
		assertNotNull(book.getLibrary());
		assertEquals(book.getLibrary().getName(), getBookResponse().getLibrary().getName());
	}

	@Test(expected = BusinessException.class)
	public void When_UpadteBooksWithInvalidId_ThenReturnBusinessException() {
		Mockito.when(bookRepository.save(ArgumentMatchers.any())).thenReturn(getBookResponse());
		bookServiceImpl.update(getBookResponse());
		Mockito.verify(bookRepository, Mockito.timeout(1)).save(ArgumentMatchers.any());
	}

	@Test
	public void When_DeleteBooks_ThenReturnSuccess() {
		Mockito.doNothing().when(bookRepository).deleteById(ArgumentMatchers.anyLong());
		Mockito.when(bookRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(getBookResponse()));
		bookServiceImpl.delete(1L);
		Mockito.verify(bookRepository, Mockito.timeout(1)).deleteById(ArgumentMatchers.anyLong());
		Mockito.verify(bookRepository, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
	}

	@Test(expected = BusinessException.class)
	public void When_DeleteBooksWithInvalidId_ThenReturnSuccess() {
		Mockito.doNothing().when(bookRepository).deleteById(ArgumentMatchers.anyLong());
		bookServiceImpl.delete(1L);
		Mockito.verify(bookRepository, Mockito.timeout(1)).deleteById(ArgumentMatchers.anyLong());
	}

	private org.springframework.data.domain.Page<Book> getPageBookResponse() {
		org.springframework.data.domain.Page<Book> page = new PageImpl<>(getBooksResponse());
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
