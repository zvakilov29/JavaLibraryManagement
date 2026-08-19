package com.zvaki.librarymanagement.mapper;

import com.zvaki.librarymanagement.domain.Book;
import com.zvaki.librarymanagement.dto.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthorName(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getStatus()
        );
    }
}
