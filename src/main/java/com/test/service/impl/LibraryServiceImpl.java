package com.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.exception.BusinessException;
import com.test.model.Library;
import com.test.repository.LibraryRepository;
import com.test.service.LibraryService;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private LibraryRepository libraryRepository;
	
	@Override
	public List<Library> findAll() {
		return libraryRepository.findAll();
	}

	@Override
	public Library findById(Long id) {
		Library library = libraryRepository.findById(id).orElse(null);
		if (library == null)
			throw new BusinessException("Library Id is invalid. Please enter valid id");
		return library;
	}

	@Override
	@Transactional
	public Library save(Library book) {
		return libraryRepository.save(book);
	}

	@Override
	@Transactional
	public Library update(Library book) {
		findById(book.getId());
		return libraryRepository.save(book);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Library library = findById(id);
		libraryRepository.delete(library);
	}

}
