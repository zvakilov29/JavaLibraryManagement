package com.zvaki.librarymanagement.service;

import com.zvaki.librarymanagement.repository.BookRepository;

import java.util.Objects;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = Objects.requireNonNull(bookRepository, "bookRepository must not be null");
    }
}
