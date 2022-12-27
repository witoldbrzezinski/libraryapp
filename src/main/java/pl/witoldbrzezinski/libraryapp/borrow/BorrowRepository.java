package pl.witoldbrzezinski.libraryapp.borrow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface BorrowRepository extends JpaRepository<BorrowEntity,Long> {

    List<BorrowEntity> findByCustomer_Id(Long id);
}
