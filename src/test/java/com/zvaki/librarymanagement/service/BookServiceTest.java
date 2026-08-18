package com.zvaki.librarymanagement.service;

import com.zvaki.librarymanagement.domain.Book;
import com.zvaki.librarymanagement.domain.BookStatus;
import com.zvaki.librarymanagement.exception.BookNotFoundException;
import com.zvaki.librarymanagement.exception.DuplicateIsbnException;
import com.zvaki.librarymanagement.repository.BookRepository;
import com.zvaki.librarymanagement.repository.InMemoryBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        BookRepository bookRepository = new InMemoryBookRepository();
        bookService = new BookService(bookRepository);
    }

    @Test
    void createBookReturnsBookWithGivenDetails() {
        Book book = bookService.createBook("Clean Code", "Robert C. Martin", "ISBN-111", 2008);

        assertEquals("Clean Code", book.getTitle());
        assertEquals("ISBN-111", book.getIsbn());
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    @Test
    void createBookWithDuplicateIsbnThrows() {
        bookService.createBook("Clean Code", "Robert C. Martin", "ISBN-111", 2008);

        assertThrows(DuplicateIsbnException.class,
                () -> bookService.createBook("Another Book", "Some Author", "ISBN-111", 2020));
    }

    @Test
    void getBookByIdThrowsWhenBookDoesNotExist() {
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(999L));
    }

    @Test
    void updateBookKeepingItsOwnIsbnSucceeds() {
        Book book = bookService.createBook("Clean Code", "Robert C. Martin", "ISBN-111", 2008);

        Book updated = bookService.updateBook(book.getId(), "Clean Code Updated",
                "Robert C. Martin", "ISBN-111", 2009);

        assertEquals("Clean Code Updated", updated.getTitle());
        assertEquals(2009, updated.getPublicationYear());
    }
}
