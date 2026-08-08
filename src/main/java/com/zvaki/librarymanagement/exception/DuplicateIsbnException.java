package com.zvaki.librarymanagement.exception;

public class DuplicateIsbnException extends LibraryException {

    public DuplicateIsbnException(String isbn) {
        super("The book with ISBN: " + isbn + " already exists");
    }
}
