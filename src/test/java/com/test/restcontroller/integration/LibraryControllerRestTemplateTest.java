package com.test.restcontroller.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class LibraryControllerRestTemplateTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void findAllLibraries_thenReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<RestResponse<List<Library>>> response = restTemplate.exchange(createURLWithPort("/v0/library/"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponse<List<Library>>>() {
		});
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody().getData());
		assertEquals(response.getBody().getData().get(0).getName(), getLibrarysResponse().get(0).getName());
		assertEquals(response.getBody().getData().get(0).getAddress(), getLibrarysResponse().get(0).getAddress());
		assertEquals(response.getBody().getData().get(0).getDesc(), getLibrarysResponse().get(0).getDesc());
		assertEquals(response.getBody().getData().get(0).getGroupName(), getLibrarysResponse().get(0).getGroupName());
		assertEquals(response.getBody().getData().get(0).getRanking(), getLibrarysResponse().get(0).getRanking());
		
	}

	@Test
	public void findLibrariesById_thenReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<RestResponse<Library>> response = restTemplate.exchange(createURLWithPort("/v0/library/1"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponse<Library>>() {
		});
		assertNotNull(response);
		assertNotNull(response.getBody());
		//assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void addLibraries_thenReturnSuccess() throws Exception {
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Library> entity = new HttpEntity<Library>(getLibraryResponse(), headers);
		ResponseEntity<RestResponse<Library>> response = restTemplate.exchange(createURLWithPort("/v0/library/"), HttpMethod.POST, entity,
				new ParameterizedTypeReference<RestResponse<Library>>() {
				});
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertNotNull(response.getBody().getData());
		assertEquals(response.getBody().getData().getName(), getLibraryResponse().getName());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void When_UpdateLibrariesByInvalidMethodType_thenReturn_BAD_REQUEST() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Library> entity = new HttpEntity<Library>(getLibraryResponse(), headers);
		ResponseEntity<RestResponse<Library>> response = restTemplate.exchange(createURLWithPort("/v0/library/3456"), HttpMethod.PUT, entity,
				new ParameterizedTypeReference<RestResponse<Library>>() {
				});
		assertNotNull(response);
		assertNull(response.getBody().getData());
		assertEquals(response.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@Test
	public void When_DeleteLibraries_thenReturnSuccess() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Book> entity = new HttpEntity<Book>(null, headers);
		ResponseEntity<RestResponse<Void>> response = restTemplate.exchange(createURLWithPort("/v0/library/1"), HttpMethod.DELETE, entity,
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