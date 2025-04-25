package com.uls.library.controller;

import com.uls.library.entity.Author;
import com.uls.library.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class AuthorController {

	@Autowired
	private AuthorRepository authorRepository;

	@PostMapping("/authors")
	public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
		log.info("Adding author: {}", author);
		return ResponseEntity.ok(authorRepository.save(author)); // HTTP 200 Ok
	}

	@GetMapping("/authors/{id}")
	public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
		log.info("Retrieving author with id: {}", id);
		return authorRepository.findById(id)
				.map(ResponseEntity::ok) // HTTP 200 Ok
				.orElseGet(() -> ResponseEntity.notFound().build()); // HTTP 404 Not Found
	}

	@GetMapping("/authors")
	public ResponseEntity<List<Author>> getAllAuthors() {
		log.info("Retrieving all authors");
		return ResponseEntity.ok(authorRepository.findAll()); // HTTP 200 Ok
	}

	@DeleteMapping("/authors/{id}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		log.info("Deleting author with id: {}", id);
		if (authorRepository.existsById(id)) {
			authorRepository.deleteById(id);
			return ResponseEntity.noContent().build(); // HTTP 204 No Content
		} else {
			return ResponseEntity.notFound().build(); // HTTP 404 Not Found
		}
	}
}
