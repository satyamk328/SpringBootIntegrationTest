package com.test.restcontroller.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookControllerMockmvcTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	public void findAllBooks_thenReturnSuccess() throws Exception {
		mockMvc.perform(get("/v0/book/", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
	}
	
	@Test
	public void findBookById_thenReturnSuccess() throws Exception {
		mockMvc.perform(get("/v0/book/{id}", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
	}
	
	
	@Test
	public void addBook_thenReturnSuccess() throws Exception {
		Book book = new Book();
		book.setName("Zaphod");

		Library library = new Library();
		library.setName("Library 1");
		book.setLibrary(library);
		mockMvc.perform(MockMvcRequestBuilders.post("/v0/book/").content(objectMapper.writeValueAsString(book))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void When_UpdateBookByInvalidId_thenReturn_BAD_REQUEST() throws Exception {
		Book book = new Book();

		Library library = new Library();
		library.setName("Library 1");
		book.setLibrary(library);

		mockMvc.perform(MockMvcRequestBuilders.post("/v0/book/").content(objectMapper.writeValueAsString(book))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updateBook_thenPassBookId_ReturnSuccess() throws Exception {
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

	@Test
	public void when_DeleteBookByInvalidId_thenReturns400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/v0/book/{id}", 123)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void When_DeleteBook_thenReturnSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/v0/book/{id}", 1)).andExpect(status().isOk());
	}
}