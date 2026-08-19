package com.zvaki.librarymanagement.service;

import com.zvaki.librarymanagement.domain.Book;
import com.zvaki.librarymanagement.exception.BookNotFoundException;
import com.zvaki.librarymanagement.exception.DuplicateIsbnException;
import com.zvaki.librarymanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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

    public Book updateBook(Long id, String title, String authorName, String isbn, Integer publicationYear) {
        Book bookToUpdate = getBookById(id);

        ensureIsbnIsNotTakenByAnotherBook(id, isbn);

        bookToUpdate.updateDetails(title, authorName, isbn, publicationYear);

        return bookRepository.save(bookToUpdate);
    }

    public void deleteBook(Long id) {
        ensureBookExists(id);
        bookRepository.deleteById(id);
    }

    private void ensureIsbnIsNotTaken(String isbn) {
        if (bookRepository.findByIsbn(isbn).isPresent()) {
            throw new DuplicateIsbnException(isbn);
        }
    }

    private void ensureIsbnIsNotTakenByAnotherBook(Long bookId, String isbn) {
        Optional<Book> foundBook = bookRepository.findByIsbn(isbn);

        if (foundBook.isEmpty()) {
            return;
        }

        if (!foundBook.get().getId().equals(bookId)) {
            throw new DuplicateIsbnException(isbn);
        }
    }

    private void ensureBookExists(Long id) {
        getBookById(id);
    }

}
