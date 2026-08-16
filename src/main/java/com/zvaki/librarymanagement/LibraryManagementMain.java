package com.zvaki.librarymanagement;

import com.zvaki.librarymanagement.domain.Book;
import com.zvaki.librarymanagement.repository.BookRepository;
import com.zvaki.librarymanagement.repository.InMemoryBookRepository;
import com.zvaki.librarymanagement.service.BookService;

public class LibraryManagementMain {
    public static void main(String[] args) {
        BookRepository bookRepository = new InMemoryBookRepository();
        BookService bookService = new BookService(bookRepository);

        Book book1 = bookService.createBook(
                "Clean Code",
                "Robert C. Martin",
                "ISBN-111",
                2008
        );

        System.out.println("Created book:");
        System.out.println(book1);

        System.out.println("\nAll books:");
        bookService.getAllBooks().forEach(System.out::println);

        Book borrowedBook = bookService.borrowBook(book1.getId());

        System.out.println("\nAfter borrowing:");
        System.out.println(borrowedBook);

        runScenario("Try to borrow the same book again", () -> {
            bookService.borrowBook(book1.getId());
        });

        Book returnedBook = bookService.returnBook(book1.getId());

        System.out.println("\nAfter returning:");
        System.out.println(returnedBook);

        Book updatedBook = bookService.updateBook(
                book1.getId(),
                "Clean Code Updated",
                "Robert C. Martin",
                "ISBN-111",
                2009
        );

        System.out.println("\nAfter update:");
        System.out.println(updatedBook);

        runScenario("Try to create another book with same ISBN", () -> {
            bookService.createBook(
                    "Another Book",
                    "Some Author",
                    "ISBN-111",
                    2020
            );
        });

        Book archivedBook = bookService.archiveBook(book1.getId());

        System.out.println("\nAfter archiving:");
        System.out.println(archivedBook);

        runScenario("Try to borrow archived book", () -> {
            bookService.borrowBook(book1.getId());
        });

        bookService.deleteBook(book1.getId());

        System.out.println("\nBook deleted.");

        runScenario("Try to get deleted book", () -> {
            bookService.getBookById(book1.getId());
        });
    }

    private static void runScenario(String scenarioName, Runnable action) {
        try {
            action.run();
            System.out.println("\n" + scenarioName + ": unexpectedly succeeded");
        } catch (RuntimeException exception) {
            System.out.println("\n" + scenarioName + ": failed as expected");
            System.out.println("Reason: " + exception.getMessage());
        }
    }
}
