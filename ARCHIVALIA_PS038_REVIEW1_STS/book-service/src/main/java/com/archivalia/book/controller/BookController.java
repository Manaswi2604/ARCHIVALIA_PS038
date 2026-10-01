package com.archivalia.book.controller;
import com.archivalia.book.model.Book;
import com.archivalia.book.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {
    private final BookRepository repo;
    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Book> all(@RequestParam(required=false) String search) {
        if (search == null || search.isBlank()) return repo.findAll();
        return repo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(search, search);
    }

    @GetMapping("/{id}") public ResponseEntity<Book> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book add(@RequestBody Book book) { return repo.save(book); }

    @PutMapping("/{id}/availability")
    public ResponseEntity<?> availability(@PathVariable Long id, @RequestParam boolean value) {
        return repo.findById(id).map(b -> {
            b.setAvailable(value); repo.save(b); return ResponseEntity.ok(b);
        }).orElse(ResponseEntity.notFound().build());
    }
}
