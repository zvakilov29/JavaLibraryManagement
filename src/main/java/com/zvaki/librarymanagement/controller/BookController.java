package com.zvaki.librarymanagement.controller;

import com.zvaki.librarymanagement.domain.Book;
import com.zvaki.librarymanagement.dto.BookResponse;
import com.zvaki.librarymanagement.dto.CreateBookRequest;
import com.zvaki.librarymanagement.dto.UpdateBookRequest;
import com.zvaki.librarymanagement.mapper.BookMapper;
import com.zvaki.librarymanagement.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public List<BookResponse> getAllBooks() {
        List<Book> books = bookService.getAllBooks();

        List<BookResponse> responses = new ArrayList<>();
        for (Book book : books) {
            responses.add(bookMapper.toResponse(book));
        }
        return responses;
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        return bookMapper.toResponse(book);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@RequestBody CreateBookRequest request) {
        Book book = bookService.createBook(
                request.title(),
                request.authorName(),
                request.isbn(),
                request.publicationYear()
        );

        return bookMapper.toResponse(book);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable Long id,
                                   @RequestBody UpdateBookRequest request) {
        Book book = bookService.updateBook(
                id,
                request.title(),
                request.authorName(),
                request.isbn(),
                request.publicationYear()
        );

        return bookMapper.toResponse(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @PostMapping("/{id}/borrow")
    public BookResponse borrowBook(@PathVariable Long id) {
        Book book = bookService.borrowBook(id);
        return bookMapper.toResponse(book);
    }

    @PostMapping("/{id}/return")
    public BookResponse returnBook(@PathVariable Long id) {
        Book book = bookService.returnBook(id);
        return bookMapper.toResponse(book);
    }

    @PostMapping("/{id}/archive")
    public BookResponse archiveBook(@PathVariable Long id) {
        Book book = bookService.archiveBook(id);
        return bookMapper.toResponse(book);
    }
}
