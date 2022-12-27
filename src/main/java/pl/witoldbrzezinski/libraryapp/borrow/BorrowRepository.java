package pl.witoldbrzezinski.libraryapp.borrow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.witoldbrzezinski.libraryapp.book.BookEntity;
import pl.witoldbrzezinski.libraryapp.customer.CustomerEntity;

import java.util.List;
public interface BorrowRepository extends JpaRepository<BorrowEntity,Long> {

    List<BorrowEntity> findByCustomer(CustomerEntity customerEntity);

    List<BorrowEntity> findByBook(BookEntity bookEntity);
}
