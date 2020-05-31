package com.test.restcontroller.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.model.Book;
import com.test.model.Library;
import com.test.request.model.RestResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookControllerRestTemplateTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void findAllBooks_thenReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort("/v0/book/"), HttpMethod.GET, entity,
				Object.class);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void findBookById_thenReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<Object> response = restTemplate.exchange(createURLWithPort("/v0/book/A001"), HttpMethod.GET, entity,
				Object.class);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void addBook_thenReturnSuccess() throws Exception {
		Book book = new Book();
		book.setName("Zaphod");
		
		Library library = new Library();
		library.setName("test");
		book.setLibrary(library);
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Book> entity = new HttpEntity<Book>(book, headers);
		ResponseEntity<RestResponse<Book>> response = restTemplate.exchange(createURLWithPort("/v0/book/"), HttpMethod.POST, entity,
				new ParameterizedTypeReference<RestResponse<Book>>() {
				});
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getData());
		assertEquals(response.getBody().getData().getName(), "Zaphod");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void updateBook_thenPassBookId_ReturnSuccess() throws Exception {
		Book book = new Book();
		book.setName("Zaphod");
		
		Library library = new Library();
		library.setName("Library");
		library.setId(1L);
		book.setLibrary(library);
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Book> entity = new HttpEntity<Book>(book, headers);
		ResponseEntity<RestResponse<Book>> response = restTemplate.exchange(createURLWithPort("/v0/book/1"), HttpMethod.PUT, entity,
				new ParameterizedTypeReference<RestResponse<Book>>() {
				});
		assertNotNull(response);
		assertNotNull(response.getBody());
	}

	@Test
	public void When_UpdateBookByInvalidId_thenReturn_BAD_REQUEST() throws Exception {
		Book book = new Book();
		book.setName("Zaphod");
		
		Library library = new Library();
		library.setName("Library");
		library.setId(1L);
		book.setLibrary(library);
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Book> entity = new HttpEntity<Book>(book, headers);
		ResponseEntity<RestResponse<Book>> response = restTemplate.exchange(createURLWithPort("/v0/book/3456"), HttpMethod.PUT, entity,
				new ParameterizedTypeReference<RestResponse<Book>>() {
				});
		assertNotNull(response);
		assertNull(response.getBody().getData());
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void When_DeleteBook_thenReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Book> entity = new HttpEntity<Book>(null, headers);
		ResponseEntity<RestResponse<Void>> response = restTemplate.exchange(createURLWithPort("/v0/book/1"), HttpMethod.DELETE, entity,
				new ParameterizedTypeReference<RestResponse<Void>>() {
				});
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertNull(response.getBody().getData());
		//assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}