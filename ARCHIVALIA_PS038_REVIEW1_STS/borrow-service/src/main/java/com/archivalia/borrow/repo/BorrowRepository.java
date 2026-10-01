package com.archivalia.borrow.repo;
import com.archivalia.borrow.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface BorrowRepository extends JpaRepository<Borrow,Long> {
 List<Borrow> findByUserEmailOrderByBorrowDateDesc(String email);
}
