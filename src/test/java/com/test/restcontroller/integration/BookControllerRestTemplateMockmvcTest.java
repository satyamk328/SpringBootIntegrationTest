package com.test.restcontroller.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.model.Book;
import com.test.model.Library;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerRestTemplateMockmvcTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testCreateUpdateAndDeleteApi() throws Exception {
		when_ValidationException_thenReturns400();
		when_AddBook_thenReturns200();
		when_findAllBooks_thenReturns200();
		when_UpdateBook_thenReturns200();
		when_DeleteBookByInvalidId_thenReturns400();
		when_DeleteBook_thenReturns200();
	}

	public void when_findAllBooks_thenReturns200() throws Exception {
		mockMvc.perform(get("/v0/book/{id}", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
	}
	
	@Test
	public void when_AddBook_thenReturns200() throws Exception {
		Book book = new Book();
		book.setName("Zaphod");

		Library library = new Library();
		library.setName("Library 1");
		book.setLibrary(library);
		mockMvc.perform(MockMvcRequestBuilders.post("/v0/book/").content(objectMapper.writeValueAsString(book))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	public void when_ValidationException_thenReturns400() throws Exception {
		Book book = new Book();

		Library library = new Library();
		library.setName("Library 1");
		book.setLibrary(library);

		mockMvc.perform(MockMvcRequestBuilders.post("/v0/book/").content(objectMapper.writeValueAsString(book))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	public void when_UpdateBook_thenReturns200() throws Exception {
		Book book = new Book();
		book.setName("Zaphod");

		Library library = new Library();
		library.setName("Library");
		library.setId(1L);
		book.setLibrary(library);
		mockMvc.perform(MockMvcRequestBuilders.put("/v0/book/{id}", 1).content(objectMapper.writeValueAsString(book))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Zaphod"));
	}

	public void when_DeleteBookByInvalidId_thenReturns400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/v0/book/{id}", 123)).andExpect(status().isBadRequest());
	}
	
	public void when_DeleteBook_thenReturns200() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/v0/book/{id}", 1)).andExpect(status().isOk());
	}
}