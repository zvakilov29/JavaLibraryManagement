package com.zvaki.librarymanagement.repository;

import com.zvaki.librarymanagement.domain.Book;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryBookRepository implements BookRepository {

    private final Map<Long, Book> booksById = new LinkedHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public Long nextId() {
        return nextId.getAndIncrement();
    }

    @Override
    public Book save(Book book) {
        booksById.put(book.getId(), book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(booksById.values());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(booksById.get(id));
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        for (Book book : booksById.values()) {
            if (book.getIsbn().equals(isbn)) {
                return Optional.of(book);
            }
        }

        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        booksById.remove(id);
    }
}
