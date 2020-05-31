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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.exception.BusinessException;
import com.test.model.Book;
import com.test.model.Library;
import com.test.repository.LibraryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class LibraryServiceImplTest {

	@Mock
	private LibraryRepository libraryRepository;

	@InjectMocks
	private LibraryServiceImpl libraryServiceImpl;

	@Test
	public void When_FindAllLibrary_ThenReturnSuccess() {
		Mockito.when(libraryRepository.findAll()).thenReturn(getLibrarysResponse());
		List<Library> libraries = libraryServiceImpl.findAll();
		Mockito.verify(libraryRepository, Mockito.timeout(1)).findAll();
		assertNotNull(libraries);
		assertEquals(libraries.get(0).getId(), getLibrarysResponse().get(0).getId());
		assertEquals(libraries.get(0).getName(), getLibrarysResponse().get(0).getName());
	}

	@Test
	public void When_FindById_ThenReturnSuccess() {
		Mockito.when(libraryRepository.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(getLibraryResponse()));
		Library library = libraryServiceImpl.findById(1L);
		Mockito.verify(libraryRepository, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
		assertNotNull(library);
		assertEquals(library.getId(), getLibraryResponse().getId());
		assertEquals(library.getName(), getLibraryResponse().getName());
	}
	
	@Test(expected = BusinessException.class)
	public void When_FindByInvalidId_ThenReturnSuccess() {
		libraryServiceImpl.findById(1L);
		Mockito.verify(libraryRepository, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
	}

	@Test
	public void When_AddBooks_ThenReturnSuccess() {
		Mockito.when(libraryRepository.save(ArgumentMatchers.any())).thenReturn(getLibraryResponse());
		Library library = libraryServiceImpl.save(new Library());
		Mockito.verify(libraryRepository, Mockito.timeout(1)).save(ArgumentMatchers.any());
		assertNotNull(library);
		assertEquals(library.getId(), getLibraryResponse().getId());
		assertEquals(library.getName(), getLibraryResponse().getName());
	}

	@Test
	public void When_UpadteBooks_ThenReturnSuccess() {
		Mockito.when(libraryRepository.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(getLibraryResponse()));
		Mockito.when(libraryRepository.save(ArgumentMatchers.any())).thenReturn(getLibraryResponse());
		Library library = libraryServiceImpl.update(getLibraryResponse());
		Mockito.verify(libraryRepository, Mockito.timeout(1)).save(ArgumentMatchers.any());
		Mockito.verify(libraryRepository, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
		assertNotNull(library);
		assertEquals(library.getId(), getLibraryResponse().getId());
		assertEquals(library.getName(), getLibraryResponse().getName());
	}

	@Test(expected = BusinessException.class)
	public void When_UpadteBooksWithInvalidId_ThenReturnBusinessException() {
		Mockito.when(libraryRepository.save(ArgumentMatchers.any())).thenReturn(getLibraryResponse());
		libraryServiceImpl.update(getLibraryResponse());
		Mockito.verify(libraryRepository, Mockito.timeout(1)).save(ArgumentMatchers.any());
	}

	@Test
	public void When_DeleteBooks_ThenReturnSuccess() {
		Mockito.doNothing().when(libraryRepository).delete(ArgumentMatchers.any(Library.class));
		Mockito.when(libraryRepository.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(getLibraryResponse()));
		libraryServiceImpl.delete(1L);
		Mockito.verify(libraryRepository, Mockito.timeout(1)).delete(ArgumentMatchers.any(Library.class));
		Mockito.verify(libraryRepository, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
	}
	
	@Test(expected = BusinessException.class)
	public void When_DeleteBooksWithInvalidId_ThenReturnSuccess() {
		Mockito.doNothing().when(libraryRepository).delete(ArgumentMatchers.any(Library.class));
		libraryServiceImpl.delete(1L);
		Mockito.verify(libraryRepository, Mockito.timeout(1)).delete(ArgumentMatchers.any(Library.class));
	}

	private List<Library> getLibrarysResponse() {
		List<Library> list = new ArrayList<>();
		list.add(getLibraryResponse());
		return list;
	}

	private Library getLibraryResponse() {
		Book book = new Book();
		book.setId(1L);
		book.setName("Java");

		Book book1 = new Book();
		book1.setId(1L);
		book1.setName("HTML");

		Library library = new Library();
		library.setName("Library");
		library.setId(1L);

		library.getBooks().add(book);
		library.getBooks().add(book1);
		return library;
	}

}
