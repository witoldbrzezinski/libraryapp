package pl.witoldbrzezinski.libraryapp.borrow;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.witoldbrzezinski.libraryapp.book.BookEntity;
import pl.witoldbrzezinski.libraryapp.customer.CustomerEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BorrowDTOResponse {

  @NotNull private BookEntity bookEntity;
  @NotNull private CustomerEntity customerEntity;
  @NotNull private LocalDate returnDate;
  private boolean isDeleted;
  private Long version;
}
