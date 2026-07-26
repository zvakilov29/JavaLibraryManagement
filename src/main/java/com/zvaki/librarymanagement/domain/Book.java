package com.zvaki.librarymanagement.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Book {
    private Long id;
    private String title;
    private String authorName;
    private String isbn;
    private Integer publicationYear;
    private BookStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Book(Long id,
                String title,
                String authorName,
                String isbn,
                Integer publicationYear) {
        this.validateDetails(title, authorName, isbn, publicationYear);
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.status = BookStatus.AVAILABLE;

        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public BookStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void updateDetails(String title,
                              String authorName,
                              String isbn,
                              Integer publicationYear) {
        this.validateDetails(title, authorName, isbn, publicationYear);

        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.publicationYear = publicationYear;

        this.markUpdated();
    }

    public void borrow() {
        if (this.status != BookStatus.AVAILABLE) {
            throw new IllegalStateException("The book is not currently available for borrowing");
        }

        this.status = BookStatus.BORROWED;
        this.markUpdated();
    }

    public void returnBook() {
        if (this.status != BookStatus.BORROWED) {
            throw new IllegalStateException("The book is either archived or already available and cannot be returned");
        }

        this.status = BookStatus.AVAILABLE;
        this.markUpdated();
    }

    public void archive() {
        // Archiving is idempotent: archiving an already-archived book is not an
        // error, since the caller's intent (out of circulation) is already met.
        if (this.status == BookStatus.ARCHIVED) {
            return;
        }

        if (this.status == BookStatus.BORROWED) {
            throw new IllegalStateException(
                    "A borrowed book cannot be archived; it must be returned first");
        }

        this.status = BookStatus.ARCHIVED;
        this.markUpdated();
    }

    private void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }

    private void validateDetails(String title, String authorName, String isbn, Integer publicationYear) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Book title must not be blank");
        }

        if (authorName == null || authorName.isBlank()) {
            throw new IllegalArgumentException("Author name must not be blank");
        }

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN must not be blank");
        }

        // publicationYear is optional: some older works have no reliably known year.
        if (publicationYear != null && publicationYear < 0) {
            throw new IllegalArgumentException("Publication year must not be negative");
        }

        if (publicationYear != null && publicationYear > LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("Publication year must not be in the future");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Book otherBook = (Book) other;
        return Objects.equals(this.id, otherBook.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
