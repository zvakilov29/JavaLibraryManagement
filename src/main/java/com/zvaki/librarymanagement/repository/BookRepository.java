package com.zvaki.librarymanagement.repository;

import com.zvaki.librarymanagement.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Long nextId();

    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findByIsbn(String isbn);

    void deleteById(Long id);
}
