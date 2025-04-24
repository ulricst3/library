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
	AuthorRepository authorRepository;

	@PostMapping("/authors")
	ResponseEntity<Author> addAuthor(@RequestBody Author author) {
		log.info("Adding author: {}", author);
		return ResponseEntity.ok(authorRepository.save(author)); // HTTP 200 Ok
	}

	@GetMapping("/authors/{id}")
	ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
		log.info("Getting author with id: {}", id);
		return authorRepository.findById(id)
				.map(ResponseEntity::ok) // HTTP 200 Ok
				.orElseGet(() -> ResponseEntity.notFound().build()); // HTTP 404 Not Found
	}

	@GetMapping("/authors")
	ResponseEntity<List<Author>> getAllAuthors() {
		log.info("Getting all authors");
		return ResponseEntity.ok(authorRepository.findAll()); // HTTP 200 Ok
	}

	@DeleteMapping("/authors/{id}")
	ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		log.info("Deleting author with id: {}", id);
		if (authorRepository.existsById(id)) {
			authorRepository.deleteById(id);
			return ResponseEntity.noContent().build(); // HTTP 204 No Content
		} else {
			return ResponseEntity.notFound().build(); // HTTP 404 Not Found
		}
	}
}
