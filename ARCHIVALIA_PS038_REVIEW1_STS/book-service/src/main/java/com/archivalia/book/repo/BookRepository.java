package com.archivalia.book.repo;
import com.archivalia.book.model.Book;
import org.springframework.data.jpa.repository.*;
import java.util.*;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}
