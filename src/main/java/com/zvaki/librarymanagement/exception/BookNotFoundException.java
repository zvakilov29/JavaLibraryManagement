package com.zvaki.librarymanagement.exception;

public class BookNotFoundException extends LibraryException {

    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }
}
