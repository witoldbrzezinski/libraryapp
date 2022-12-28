package pl.witoldbrzezinski.libraryapp.borrow;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BorrowDtoRequest {
  @NotNull private Long bookId;
  @NotNull private Long customerId;
  @NotNull private LocalDate returnDate;
}
