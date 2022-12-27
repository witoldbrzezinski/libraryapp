package pl.witoldbrzezinski.libraryapp.borrow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import pl.witoldbrzezinski.libraryapp.book.BookEntity;
import pl.witoldbrzezinski.libraryapp.customer.CustomerEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "borrows")
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
public class BorrowEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private BookEntity bookEntity;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private CustomerEntity customerEntity;

  private LocalDate returnDate;
  private boolean isDeleted;

  private final String uuid = UUID.randomUUID().toString();
  @Version private Long version;

}
