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
public class BorrowDTOResponse {

  @NotNull private Long bookId;
  @NotNull private String bookTitle;
  @NotNull private String bookIndex;
  @NotNull private Long customerId;
  @NotNull private String customerFirstName;
  @NotNull private String customerLastName;

  @NotNull private LocalDate borrowDate;
  @NotNull private LocalDate returnDate;
  private Long version;
}
