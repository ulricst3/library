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

	@GetMapping("books/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
		log.info("Retrieving book with id: {}", bookId);
		return bookRepository.findById(bookId)
				.map(ResponseEntity::ok) // HTTP 200 Ok
				.orElseGet(() -> ResponseEntity.notFound().build()); // HTTP 404 Not Found
	}

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		log.info("Retrieving all books");
		return ResponseEntity.ok(bookRepository.findAll()); // HTTP 200 Ok
	}

	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
		log.info("Deleting author with id: {}", bookId);
		if (bookRepository.existsById(bookId)) {
			bookRepository.deleteById(bookId);
			return ResponseEntity.noContent().build(); // HTTP 204 No Content
		} else {
			return ResponseEntity.notFound().build(); // HTTP 404 Not Found
		}
	}

}
