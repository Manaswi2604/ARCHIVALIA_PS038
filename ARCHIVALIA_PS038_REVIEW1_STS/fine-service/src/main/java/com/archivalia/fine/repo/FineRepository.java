package com.archivalia.fine.repo;
import com.archivalia.fine.model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine,Long> {
 Optional<Fine> findByBorrowId(Long borrowId);
}
