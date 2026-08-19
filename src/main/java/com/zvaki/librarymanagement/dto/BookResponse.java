package com.zvaki.librarymanagement.dto;

import com.zvaki.librarymanagement.domain.BookStatus;

public record BookResponse(
        Long id,
        String title,
        String authorName,
        String isbn,
        Integer publicationYear,
        BookStatus status
) {
}