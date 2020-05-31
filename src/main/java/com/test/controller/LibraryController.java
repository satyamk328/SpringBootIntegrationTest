package com.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.Library;
import com.test.service.LibraryService;
import com.text.request.model.RestCustom;
import com.text.request.model.RestResponse;
import com.text.request.model.RestStatus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v0/library")
@Api(value = "LibraryController")
public class LibraryController {

	interface Constants {
		String FETCH_RECORDS = "Record fetch successfully";
		String NOT_FOUND = "Record not found";
		String ADD_RECORD = "Record added successfully";
		String UPDATE_RECORD = "Record updated successfully";
		String DELETE_MSG = "Record deleted successfully";
	}

	@Autowired
	private LibraryService libraryService;
	
	@ApiOperation(value = "Get All Libraries")
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
	public ResponseEntity<RestResponse<?>> findAll() {

		RestStatus<?> restStatus = new RestStatus<String>(HttpStatus.OK, Constants.FETCH_RECORDS);
		List<Library> libraries = libraryService.findAll();
		RestResponse<?> response = new RestResponse<>(libraries, restStatus, RestCustom.builder().build());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Get Library By Id")
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
		Library library = libraryService.findById(id);
		RestResponse<?> response = new RestResponse<>(library, restStatus, RestCustom.builder().build());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Add Library")
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
	public ResponseEntity<RestResponse<?>> addLibrary(@RequestBody(required = true) @Valid Library library) {

		RestStatus<?> restStatus = new RestStatus<String>(HttpStatus.CREATED, Constants.ADD_RECORD);

		library = libraryService.save(library);
		RestResponse<?> response = new RestResponse<>(library, restStatus, RestCustom.builder().build());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Library")
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
		libraryService.delete(id);
		RestResponse<?> response = new RestResponse<>(null, restStatus, RestCustom.builder().build());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}