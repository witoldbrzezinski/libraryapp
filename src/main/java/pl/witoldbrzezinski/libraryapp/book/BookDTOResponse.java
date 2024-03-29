package pl.witoldbrzezinski.libraryapp.book;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookDTOResponse {

  private Long id;
  @NotNull private String isbn;
  @NotNull private String title;
  @NotNull private String author;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Genre genre;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Status status;

  @NotNull private String index;

  private LocalDate endOfLastBorrow;
  private boolean isDeleted;
  private Long version;
}
