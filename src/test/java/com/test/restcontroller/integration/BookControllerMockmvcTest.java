package com.test.restcontroller.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.model.Book;
import com.test.model.Library;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookControllerMockmvcTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void test() {}
	//@Test
	public void execute() throws Exception {
		addBook_thenReturnSuccess();
		findAllBooks_thenReturnSuccess();
		findBookById_thenReturnSuccess();
		When_UpdateBookByInvalidId_thenReturn_BAD_REQUEST();
		updateBook_thenPassBookId_ReturnSuccess();
		when_DeleteBookByInvalidId_thenReturns400();
		When_DeleteBook_thenReturnSuccess();
	}

	// @Test
	public void findAllBooks_thenReturnSuccess() throws Exception {
		mockMvc.perform(get("/v0/book/", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value(getBookResponse().getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].desc").value(getBookResponse().getDesc()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].authorName").value(getBookResponse().getAuthorName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].status").value(getBookResponse().getStatus()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].library.address").value(getBookResponse().getLibrary().getAddress()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].library.desc").value(getBookResponse().getLibrary().getDesc()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].library.groupName").value(getBookResponse().getLibrary().getGroupName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].library.name").value(getBookResponse().getLibrary().getName()));
		
	}

	// @Test
	public void findBookById_thenReturnSuccess() throws Exception {
		mockMvc.perform(get("/v0/book/{id}", 1).accept(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(getBookResponse().getName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.desc").value(getBookResponse().getDesc()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.authorName").value(getBookResponse().getAuthorName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(getBookResponse().getStatus()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.address").value(getBookResponse().getLibrary().getAddress()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.desc").value(getBookResponse().getLibrary().getDesc()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.groupName").value(getBookResponse().getLibrary().getGroupName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.name").value(getBookResponse().getLibrary().getName()));
	}

	/// @Test
	public void addBook_thenReturnSuccess() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/v0/book/").content(objectMapper.writeValueAsString(getBookResponse()))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(getBookResponse().getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.desc").value(getBookResponse().getDesc()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.authorName").value(getBookResponse().getAuthorName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(getBookResponse().getStatus()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.address").value(getBookResponse().getLibrary().getAddress()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.desc").value(getBookResponse().getLibrary().getDesc()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.groupName").value(getBookResponse().getLibrary().getGroupName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.name").value(getBookResponse().getLibrary().getName()));
	}

	// @Test
	public void When_UpdateBookByInvalidId_thenReturn_BAD_REQUEST() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.put("/v0/book/123").content(objectMapper.writeValueAsString(getBookResponse()))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// @Test
	public void updateBook_thenPassBookId_ReturnSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/v0/book/{id}", 1)
				.content(objectMapper.writeValueAsString(getBookResponse())).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(getBookResponse().getName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.desc").value(getBookResponse().getDesc()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.authorName").value(getBookResponse().getAuthorName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(getBookResponse().getStatus()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.address").value(getBookResponse().getLibrary().getAddress()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.desc").value(getBookResponse().getLibrary().getDesc()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.groupName").value(getBookResponse().getLibrary().getGroupName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.library.name").value(getBookResponse().getLibrary().getName()));
	}

	// @Test
	public void when_DeleteBookByInvalidId_thenReturns400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/v0/book/{id}", 123)).andExpect(status().isBadRequest());
	}

	// @Test
	public void When_DeleteBook_thenReturnSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/v0/book/{id}", 1)).andExpect(status().isOk());
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