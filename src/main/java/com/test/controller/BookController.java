package com.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.Book;
import com.test.request.model.RestResponse;
import com.test.request.model.RestStatus;
import com.test.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v0/book")
@Api(value = "BookController")
public class BookController {

	interface Constants {
		String FETCH_RECORDS = "Record fetch successfully";
		String NOT_FOUND = "Record not found";
		String ADD_RECORD = "Record added successfully";
		String UPDATE_RECORD = "Record updated successfully";
		String DELETE_MSG = "Record deleted successfully";
	}
	
	@Autowired
	private BookService bookService;

	@ApiOperation(value = "Search By Book Name")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Your request was successful"),
			@ApiResponse(code = 400, message = "Your request is not accepted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 408, message = "Your request has timed out"),
			@ApiResponse(code = 409, message = "There was a resource conflict"),
			@ApiResponse(code = 500, message = "Generic server error"),
			@ApiResponse(code = 503, message = "Server Unavailable timeout") })
	@GetMapping("/search")
	public ResponseEntity<RestResponse<?>> searchBook(
			@RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", required = false, defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = "createdDate") String sortBy,
			@RequestParam(name = "searchText", required = false) String searchText) {

		RestStatus<?> restStatus = new RestStatus<String>(HttpStatus.OK, Constants.FETCH_RECORDS);
		Page<Book> page = bookService.findAll(pageNo, pageSize, sortBy, searchText);
		RestResponse<?> response = new RestResponse<>(page, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get All Books")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Your request was successful"),
			@ApiResponse(code = 400, message = "Your request is not accepted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 408, message = "Your request has timed out"),
			@ApiResponse(code = 409, message = "There was a resource conflict"),
			@ApiResponse(code = 500, message = "Generic server error"),
			@ApiResponse(code = 503, message = "Server Unavailable timeout") })
	@GetMapping("/")
	public ResponseEntity<RestResponse<List<Book>>> findAll() {

		RestStatus<?> restStatus = new RestStatus<String>(HttpStatus.OK, Constants.FETCH_RECORDS);
		List<Book> bookes = bookService.findAll();
		RestResponse<List<Book>> response = new RestResponse<>(bookes, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Get Book By Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Your request was successful"),
			@ApiResponse(code = 400, message = "Your request is not accepted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 408, message = "Your request has timed out"),
			@ApiResponse(code = 409, message = "There was a resource conflict"),
			@ApiResponse(code = 500, message = "Generic server error"),
			@ApiResponse(code = 503, message = "Server Unavailable timeout") })
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<?>> findById(@PathVariable(name = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<String>(HttpStatus.OK, Constants.FETCH_RECORDS);
		Book book = bookService.findById(id);
		RestResponse<?> response = new RestResponse<>(book, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Add Book")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Your request was successful"),
			@ApiResponse(code = 400, message = "Your request is not accepted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 408, message = "Your request has timed out"),
			@ApiResponse(code = 409, message = "There was a resource conflict"),
			@ApiResponse(code = 500, message = "Generic server error"),
			@ApiResponse(code = 503, message = "Server Unavailable timeout") })
	@PostMapping("/")
	public ResponseEntity<RestResponse<?>> addBook(@RequestBody(required = true) @Valid Book book) {

		RestStatus<?> restStatus = new RestStatus<String>(HttpStatus.CREATED, Constants.ADD_RECORD);
		book = bookService.save(book);
		RestResponse<?> response = new RestResponse<>(book, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Update Book")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Your request was successful"),
			@ApiResponse(code = 400, message = "Your request is not accepted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 408, message = "Your request has timed out"),
			@ApiResponse(code = 409, message = "There was a resource conflict"),
			@ApiResponse(code = 500, message = "Generic server error"),
			@ApiResponse(code = 503, message = "Server Unavailable timeout") })
	@PutMapping("/{id}")
	public ResponseEntity<RestResponse<?>> update(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) @Valid Book book) {

		RestStatus<?> restStatus = new RestStatus<String>(HttpStatus.OK, Constants.UPDATE_RECORD);
		book.setId(id);
		book = bookService.update(book);
		RestResponse<?> response = new RestResponse<>(book, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Book")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Your request was successful"),
			@ApiResponse(code = 400, message = "Your request is not accepted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 408, message = "Your request has timed out"),
			@ApiResponse(code = 409, message = "There was a resource conflict"),
			@ApiResponse(code = 500, message = "Generic server error"),
			@ApiResponse(code = 503, message = "Server Unavailable timeout") })
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<?>> deleteById(@PathVariable(value = "id", required = true) Long id) {

		RestStatus<?> restStatus = new RestStatus<String>(HttpStatus.OK, Constants.DELETE_MSG);
		bookService.delete(id);
		RestResponse<?> response = new RestResponse<>(null, restStatus);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
