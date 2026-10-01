package com.archivalia.fine.controller;
import com.archivalia.fine.model.Fine;
import com.archivalia.fine.repo.FineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.temporal.ChronoUnit;
import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/api/fines")
@CrossOrigin
public class FineController {
 private final FineRepository repo;

 public FineController(FineRepository repo) {
     this.repo = repo;
 }
 private static final long DAILY_FINE = 5;

 @GetMapping("/borrow/{borrowId}")
 public Fine get(@PathVariable Long borrowId) {
   return repo.findByBorrowId(borrowId).orElse(null);
 }

 @PostMapping("/calculate")
 public Fine calculate(@RequestBody FineRequest req) {
   long daysLate = Math.max(0, ChronoUnit.DAYS.between(req.dueDate(), req.returnedDate()));
   Fine fine = repo.findByBorrowId(req.borrowId()).orElse(
       Fine.builder().borrowId(req.borrowId()).userEmail(req.userEmail()).dueDate(req.dueDate()).build());
   fine.setReturnedDate(req.returnedDate());
   fine.setFineAmount(daysLate * DAILY_FINE);
   return repo.save(fine);
 }

 @GetMapping
 public List<Fine> all() { return repo.findAll(); }

 public record FineRequest(Long borrowId, String userEmail, LocalDate dueDate, LocalDate returnedDate) {}
}
