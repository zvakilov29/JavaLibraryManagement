package com.zvaki.librarymanagement.dto;

public record UpdateBookRequest(
        String title,
        String authorName,
        String isbn,
        Integer publicationYear
) {
}
