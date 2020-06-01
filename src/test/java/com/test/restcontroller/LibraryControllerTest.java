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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.controller.LibraryController;
import com.test.model.Book;
import com.test.model.Library;
import com.test.request.model.RestResponse;
import com.test.service.impl.LibraryServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class LibraryControllerTest {

	@InjectMocks
	private LibraryController libraryController;
	
	@Mock
	private LibraryServiceImpl libraryServiceImpl;
	

	@Test
	public void When_FindAllLibrarys_ThenReturnSuccess() {
		Mockito.when(libraryServiceImpl.findAll()).thenReturn(getLibrarysResponse());
		ResponseEntity<RestResponse<List<Library>>> responseEntity = libraryController.findAll();
		Mockito.verify(libraryServiceImpl, Mockito.timeout(1)).findAll();
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(responseEntity.getBody().getData().get(0).getId(), getLibrarysResponse().get(0).getId());
		assertEquals(responseEntity.getBody().getData().get(0).getName(), getLibrarysResponse().get(0).getName());
		assertEquals(responseEntity.getBody().getData().get(0).getAddress(), getLibrarysResponse().get(0).getAddress());
		assertEquals(responseEntity.getBody().getData().get(0).getDesc(), getLibrarysResponse().get(0).getDesc());
		assertEquals(responseEntity.getBody().getData().get(0).getGroupName(), getLibrarysResponse().get(0).getGroupName());
		assertEquals(responseEntity.getBody().getData().get(0).getRanking(), getLibrarysResponse().get(0).getRanking());
	}

	@Test
	public void When_FindId_ThenReturnSuccess() {
		Mockito.when(libraryServiceImpl.findById(ArgumentMatchers.anyLong())).thenReturn(getLibraryResponse());
		ResponseEntity<RestResponse<Library>> responseEntity = libraryController.findById(1L);
		Mockito.verify(libraryServiceImpl, Mockito.timeout(1)).findById(ArgumentMatchers.anyLong());
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(responseEntity.getBody().getData().getId(), getLibraryResponse().getId());
		assertEquals(responseEntity.getBody().getData().getName(), getLibraryResponse().getName());
	}


	@Test
	public void When_AddLibrary_ThenReturnSuccess() {
		Mockito.when(libraryServiceImpl.save(ArgumentMatchers.any(Library.class))).thenReturn(getLibraryResponse());
		ResponseEntity<RestResponse<Library>> responseEntity = libraryController.addLibrary(new Library());
		Mockito.verify(libraryServiceImpl, Mockito.timeout(1)).save(ArgumentMatchers.any(Library.class));
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(responseEntity.getBody().getData().getId(), getLibraryResponse().getId());
		assertEquals(responseEntity.getBody().getData().getName(), getLibraryResponse().getName());
	}
	
	@Test
	public void When_DeleteLibrary_ThenReturnSuccess() {
		Mockito.doNothing().when(libraryServiceImpl).delete(ArgumentMatchers.anyLong());
		ResponseEntity<RestResponse<?>> responseEntity = libraryController.deleteById(1L);
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
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
		book.setAuthorName("KP Thakur");
		book.setDesc("Created sample request");
		book.setStatus(true);
		book.setPrice(new BigDecimal(120));
		
		Book book1 = new Book();
		book.setId(2L);
		book.setName("HTML");
		book.setAuthorName("KPL Thakur");
		book.setDesc("Created sample request");
		book.setStatus(true);
		book.setPrice(new BigDecimal(220));

		Library library = new Library();
		library.setId(1L);
		library.setName("Library");
		library.setDesc("Created sample request");
		library.setGroupName("test");
		library.setRanking(12L);
		library.setAddress("Lodhi Road");
		library.setStatus(true);

		library.getBooks().add(book);
		library.getBooks().add(book1);
		return library;
	}

}
