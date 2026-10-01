package com.archivalia.book.config;
import com.archivalia.book.model.Book;
import com.archivalia.book.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration 
public class DataInitializer {
    private final BookRepository repo;
    public DataInitializer(BookRepository repo) {
        this.repo = repo;
    }
    @Bean CommandLineRunner seedBooks() {
        return args -> {
            if (repo.count() == 0) {
                repo.save(Book.builder().title("Spring Boot in Action").author("Craig Walls").category("Java").isbn("9781617297571").available(true).build());
                repo.save(Book.builder().title("Effective Java").author("Joshua Bloch").category("Java").isbn("9780134685991").available(true).build());
                repo.save(Book.builder().title("Clean Code").author("Robert C. Martin").category("Software Engineering").isbn("9780132350884").available(true).build());
                repo.save(Book.builder().title("Designing Data-Intensive Applications").author("Martin Kleppmann").category("Systems").isbn("9781449373320").available(true).build());
                repo.save(Book.builder().title("Computer Networks").author("Andrew S. Tanenbaum").category("Networks").isbn("9780132126953").available(true).build());
            }
        };
    }
}
