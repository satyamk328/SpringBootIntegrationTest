package com.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.model.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

	@Query("From Library r where lower(r.name)=:name")
	Optional<Library> findByName(String name);

}
