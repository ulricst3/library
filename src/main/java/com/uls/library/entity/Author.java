package com.uls.library.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = "books")
@NoArgsConstructor
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer publishedBooks;
	private Boolean active;
	private LocalDateTime birthday;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Book> books = new ArrayList<>();

	public Author(String name, Integer publishedBooks, Boolean active, LocalDateTime birthday) {
		this.name = name;
		this.publishedBooks = publishedBooks;
		this.active = active;
		this.birthday = birthday;
	}
}
