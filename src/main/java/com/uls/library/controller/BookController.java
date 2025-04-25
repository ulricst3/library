package com.uls.library.controller;

import com.uls.library.entity.Author;
import com.uls.library.entity.Book;
import com.uls.library.repository.AuthorRepository;
import com.uls.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class BookController {

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private BookRepository bookRepository;

	@PostMapping("/authors/{authorId}/books")
	public ResponseEntity<Book> addBookToAuthor(
			@PathVariable Long authorId,
			@RequestBody Book book) {
		Author author = authorRepository.findById(authorId).orElse(null);
		if (author == null) {
			return ResponseEntity.notFound().build(); // HTTP 404 Not Found
		}
		book.setAuthor(author);
		return ResponseEntity.ok(bookRepository.save(book)); // HTTP 200 Ok
	}

	@GetMapping("books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		log.info("Retrieving book with id: {}", id);
		return bookRepository.findById(id)
				.map(ResponseEntity::ok) // HTTP 200 Ok
				.orElseGet(() -> ResponseEntity.notFound().build()); // HTTP 404 Not Found
	}

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		log.info("Retrieving all books");
		return ResponseEntity.ok(bookRepository.findAll()); // HTTP 200 Ok
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		log.info("Deleting author with id: {}", id);
		if (bookRepository.existsById(id)) {
			bookRepository.deleteById(id);
			return ResponseEntity.noContent().build(); // HTTP 204 No Content
		} else {
			return ResponseEntity.notFound().build(); // HTTP 404 Not Found
		}
	}

}
