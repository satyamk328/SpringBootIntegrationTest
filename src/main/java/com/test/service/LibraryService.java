package com.test.service;

import java.util.List;

import com.test.model.Library;

public interface LibraryService {

	public List<Library> findAll();

	public Library findById(Long id);

	public Library save(Library book);

	public Library update(Library book);

	public void delete(Long id);
	
}
