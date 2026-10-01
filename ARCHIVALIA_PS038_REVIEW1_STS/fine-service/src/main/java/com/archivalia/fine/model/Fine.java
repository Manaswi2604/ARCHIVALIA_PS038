package com.archivalia.fine.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fines")
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long borrowId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate returnedDate;

    @Column(nullable = false)
    private long fineAmount;

    // No-argument constructor required by JPA
    public Fine() {
    }

    // All-argument constructor
    public Fine(Long id, Long borrowId, String userEmail,
                LocalDate dueDate, LocalDate returnedDate,
                long fineAmount) {
        this.id = id;
        this.borrowId = borrowId;
        this.userEmail = userEmail;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
        this.fineAmount = fineAmount;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getBorrowId() {
        return borrowId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public long getFineAmount() {
        return fineAmount;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public void setFineAmount(long fineAmount) {
        this.fineAmount = fineAmount;
    }

    // Builder
    public static FineBuilder builder() {
        return new FineBuilder();
    }

    public static class FineBuilder {

        private Long id;
        private Long borrowId;
        private String userEmail;
        private LocalDate dueDate;
        private LocalDate returnedDate;
        private long fineAmount;

        public FineBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public FineBuilder borrowId(Long borrowId) {
            this.borrowId = borrowId;
            return this;
        }

        public FineBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public FineBuilder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public FineBuilder returnedDate(LocalDate returnedDate) {
            this.returnedDate = returnedDate;
            return this;
        }

        public FineBuilder fineAmount(long fineAmount) {
            this.fineAmount = fineAmount;
            return this;
        }

        public Fine build() {
            return new Fine(
                id,
                borrowId,
                userEmail,
                dueDate,
                returnedDate,
                fineAmount
            );
        }
    }
}