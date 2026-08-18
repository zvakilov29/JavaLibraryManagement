package com.zvaki.librarymanagement.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {



    @Test
    void newBookIsAvailable() {
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", "ISBN-111", 2008);

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    @Test
    void borrowingAnAvailableBookMakesItBorrowed() {
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", "ISBN-111", 2008);

        book.borrow();

        assertEquals(BookStatus.BORROWED, book.getStatus());
    }

    @Test
    void borrowingAnAlreadyBorrowedBookThrows() {
        Book book = new Book(1L, "Clean Code", "Robert C. Martin", "ISBN-111", 2008);

        book.borrow();

        assertThrows(IllegalStateException.class, () -> book.borrow());
    }
}
