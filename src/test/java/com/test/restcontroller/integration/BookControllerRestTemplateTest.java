package com.test.restcontroller.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.List;

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
		ResponseEntity<RestResponse<List<Book>>> responseEntity = restTemplate.exchange(createURLWithPort("/v0/book/"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponse<List<Book>>>() {
		});
		assertNotNull(responseEntity);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody().getData());
		assertEquals(responseEntity.getBody().getData().get(0).getAuthorName(), getBookResponse().getAuthorName());
		assertEquals(responseEntity.getBody().getData().get(0).getDesc(), getBookResponse().getDesc());
		assertEquals(responseEntity.getBody().getData().get(0).getName(), getBookResponse().getName());
		assertEquals(responseEntity.getBody().getData().get(0).getStatus(), getBookResponse().getStatus());
		assertEquals(responseEntity.getBody().getData().get(0).getLibrary().getAddress(), getBookResponse().getLibrary().getAddress());
		assertEquals(responseEntity.getBody().getData().get(0).getLibrary().getDesc(), getBookResponse().getLibrary().getDesc());
		assertEquals(responseEntity.getBody().getData().get(0).getLibrary().getGroupName(), getBookResponse().getLibrary().getGroupName());
		assertEquals(responseEntity.getBody().getData().get(0).getLibrary().getName(), getBookResponse().getLibrary().getName());
	}

	@Test
	public void findBookById_thenReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<RestResponse<Book>> responseEntity = restTemplate.exchange(createURLWithPort("/v0/book/12"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponse<Book>>() {
		});
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
	public void addBook_thenReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Book> entity = new HttpEntity<Book>(getBookResponse(), headers);
		ResponseEntity<RestResponse<Book>> response = restTemplate.exchange(createURLWithPort("/v0/book/"), HttpMethod.POST, entity,
				new ParameterizedTypeReference<RestResponse<Book>>() {
				});
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getData());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getData().getName(), getBookResponse().getName());
		assertEquals(response.getBody().getData().getDesc(), getBookResponse().getDesc());
		assertEquals(response.getBody().getData().getPrice(), getBookResponse().getPrice());
		assertEquals(response.getBody().getData().getStatus(), getBookResponse().getStatus());
		assertEquals(response.getBody().getData().getLibrary().getAddress(), getBookResponse().getLibrary().getAddress());
		assertEquals(response.getBody().getData().getLibrary().getGroupName(), getBookResponse().getLibrary().getGroupName());
		assertEquals(response.getBody().getData().getLibrary().getRanking(), getBookResponse().getLibrary().getRanking());
	}
	
	@Test
	public void updateBook_thenPassBookId_ReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Book> entity = new HttpEntity<Book>(getBookResponse(), headers);
		ResponseEntity<RestResponse<Book>> response = restTemplate.exchange(createURLWithPort("/v0/book/12"), HttpMethod.PUT, entity,
				new ParameterizedTypeReference<RestResponse<Book>>() {
				});
		assertNotNull(response);
		assertNotNull(response.getBody());
	}

	@Test
	public void When_UpdateBookByInvalidId_thenReturn_BAD_REQUEST() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Book> entity = new HttpEntity<Book>(getBookResponse(), headers);
		ResponseEntity<RestResponse<Book>> response = restTemplate.exchange(createURLWithPort("/v0/book/3456"), HttpMethod.PUT, entity,
				new ParameterizedTypeReference<RestResponse<Book>>() {
				});
		assertNotNull(response);
		assertNull(response.getBody().getData());
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void test_DeleteBook_thenReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Book> entity = new HttpEntity<Book>(null, headers);
		ResponseEntity<RestResponse<Void>> response = restTemplate.exchange(createURLWithPort("/v0/book/12"), HttpMethod.DELETE, entity,
				new ParameterizedTypeReference<RestResponse<Void>>() {
				});
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertNull(response.getBody().getData());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	private Book getBookResponse() {
		Book book = new Book();
		book.setId(12L);
		book.setName("Java");
		book.setAuthorName("KP Thakur");
		book.setDesc("Created sample request");
		book.setStatus(true);
		book.setPrice(new BigDecimal(120));

		Library library = new Library();
		library.setId(12L);
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