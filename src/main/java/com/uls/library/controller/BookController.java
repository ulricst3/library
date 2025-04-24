package com.uls.library.controller;

import com.uls.library.entity.Author;
import com.uls.library.entity.Book;
import com.uls.library.repository.AuthorRepository;
import com.uls.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class BookController {

	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	BookRepository bookRepository;

	@PostMapping("/authors/{authorId}/books")
	public ResponseEntity<Book> addBookToAuthor(
			@PathVariable Long authorId,
			@RequestBody Book book) {
		Author author = authorRepository.findById(authorId).orElse(null);
		if (author == null) {
			return ResponseEntity.notFound().build();
		}
		book.setAuthor(author);
		return ResponseEntity.ok(bookRepository.save(book));
	}

}
