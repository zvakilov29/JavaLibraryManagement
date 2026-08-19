package com.zvaki.librarymanagement.dto;

public record CreateBookRequest(
        String title,
        String authorName,
        String isbn,
        Integer publicationYear
) {
}