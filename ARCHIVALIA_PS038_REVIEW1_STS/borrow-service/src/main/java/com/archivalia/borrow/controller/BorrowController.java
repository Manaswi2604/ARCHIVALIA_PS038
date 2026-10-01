package com.archivalia.borrow.controller;

import com.archivalia.borrow.model.Borrow;
import com.archivalia.borrow.repo.BorrowRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrow")
@CrossOrigin
public class BorrowController {

    private final BorrowRepository repo;
    private final RestTemplate rest;

    public BorrowController(BorrowRepository repo, RestTemplate rest) {
        this.repo = repo;
        this.rest = rest;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
            "service", "BORROW-SERVICE",
            "status", "UP"
        );
    }

    @PostMapping
    public ResponseEntity<?> borrow(
            @RequestBody BorrowRequest req,
            Authentication auth) {

        String email = auth.getName();

        Map<?, ?> book;

        try {
            book = rest.getForObject(
                "http://BOOK-SERVICE/api/books/" + req.bookId(),
                Map.class
            );
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity
                .status(503)
                .body(Map.of(
                    "message", "Book Service unavailable",
                    "error", e.getMessage()
                ));
        }

        if (book == null || !Boolean.TRUE.equals(book.get("available"))) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("message", "Book is not available"));
        }

        LocalDate now = LocalDate.now();

        Borrow b = Borrow.builder()
            .bookId(req.bookId())
            .userEmail(email)
            .borrowDate(now)
            .dueDate(now.plusDays(
                req.loanDays() == null ? 7 : req.loanDays()
            ))
            .status("BORROWED")
            .build();

        Borrow saved = repo.save(b);

        rest.put(
            "http://BOOK-SERVICE/api/books/"
                + req.bookId()
                + "/availability?value=false",
            null
        );

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/my")
    public List<Borrow> mine(Authentication auth) {
        return repo.findByUserEmailOrderByBorrowDateDesc(
            auth.getName()
        );
    }

    @GetMapping
    public List<Borrow> all() {
        return repo.findAll();
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<?> returnBook(
            @PathVariable Long id,
            Authentication auth) {

        Borrow b = repo.findById(id).orElse(null);

        if (b == null) {
            return ResponseEntity.notFound().build();
        }

        boolean isAdmin = auth.getAuthorities()
            .stream()
            .anyMatch(a ->
                a.getAuthority().equals("ROLE_ADMIN")
            );

        if (!b.getUserEmail().equals(auth.getName()) && !isAdmin) {
            return ResponseEntity
                .status(403)
                .body(Map.of("message", "Not allowed"));
        }

        if ("RETURNED".equals(b.getStatus())) {
            return ResponseEntity.ok(b);
        }

        b.setReturnDate(LocalDate.now());
        b.setStatus("RETURNED");

        repo.save(b);

        // Make book available again
        rest.put(
            "http://BOOK-SERVICE/api/books/"
                + b.getBookId()
                + "/availability?value=true",
            null
        );

        // Calculate fine
        FineRequest fineRequest = new FineRequest(
            b.getId(),
            b.getUserEmail(),
            b.getDueDate(),
            b.getReturnDate()
        );

        rest.postForObject(
            "http://FINE-SERVICE/api/fines/calculate",
            fineRequest,
            Void.class
        );

        return ResponseEntity.ok(b);
    }

    public record BorrowRequest(
        Long bookId,
        Integer loanDays
    ) {}

    public record FineRequest(
        Long borrowId,
        String userEmail,
        LocalDate dueDate,
        LocalDate returnedDate
    ) {}
}