package com.test.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
	@Query(value = "FROM Book r WHERE lower(r.name) like %?1% ")
	Page<Book> findByfullTextSearch(String text, Pageable pageable);
	
	@Query(value = "select * FROM book r WHERE r.library_id=:libId", nativeQuery = true)
	public List<Book> findByLiberyId(Long libId);

}

