package com.zvaki.librarymanagement.service;

import com.zvaki.librarymanagement.domain.Book;
import com.zvaki.librarymanagement.exception.BookNotFoundException;
import com.zvaki.librarymanagement.exception.DuplicateIsbnException;
import com.zvaki.librarymanagement.repository.BookRepository;

import java.util.List;
import java.util.Objects;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = Objects.requireNonNull(bookRepository, "bookRepository must not be null");
    }

    public Book createBook(String title, String authorName, String isbn, Integer publicationYear) {
        ensureIsbnIsNotTaken(isbn);
        Long newId = bookRepository.nextId();
        Book newBook = new Book(newId, title, authorName, isbn, publicationYear);
        return bookRepository.save(newBook);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book borrowBook(Long id) {
        Book bookToBorrow = getBookById(id);
        bookToBorrow.borrow();
        return bookRepository.save(bookToBorrow);
    }

    public Book returnBook(Long id) {
        Book bookToReturn = getBookById(id);
        bookToReturn.returnBook();
        return bookRepository.save(bookToReturn);
    }

    public Book archiveBook(Long id) {
        Book bookToArchive = getBookById(id);
        bookToArchive.archive();
        return bookRepository.save(bookToArchive);
    }

    private void ensureIsbnIsNotTaken(String isbn) {
        if (bookRepository.findByIsbn(isbn).isPresent()) {
            throw new DuplicateIsbnException(isbn);
        }
    }

}
