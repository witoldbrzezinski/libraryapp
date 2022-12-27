package pl.witoldbrzezinski.libraryapp.borrow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<BorrowEntity,Long> {
}
