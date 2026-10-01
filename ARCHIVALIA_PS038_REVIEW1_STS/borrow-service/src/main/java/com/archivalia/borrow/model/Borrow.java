package com.archivalia.borrow.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrows")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate returnDate;

    @Column(nullable = false)
    private String status;

    // No-argument constructor required by JPA
    public Borrow() {
    }

    // All-argument constructor
    public Borrow(Long id, Long bookId, String userEmail,
                  LocalDate borrowDate, LocalDate dueDate,
                  LocalDate returnDate, String status) {
        this.id = id;
        this.bookId = bookId;
        this.userEmail = userEmail;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Builder
    public static BorrowBuilder builder() {
        return new BorrowBuilder();
    }

    public static class BorrowBuilder {

        private Long id;
        private Long bookId;
        private String userEmail;
        private LocalDate borrowDate;
        private LocalDate dueDate;
        private LocalDate returnDate;
        private String status;

        public BorrowBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BorrowBuilder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public BorrowBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public BorrowBuilder borrowDate(LocalDate borrowDate) {
            this.borrowDate = borrowDate;
            return this;
        }

        public BorrowBuilder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public BorrowBuilder returnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public BorrowBuilder status(String status) {
            this.status = status;
            return this;
        }

        public Borrow build() {
            return new Borrow(
                id,
                bookId,
                userEmail,
                borrowDate,
                dueDate,
                returnDate,
                status
            );
        }
    }
}