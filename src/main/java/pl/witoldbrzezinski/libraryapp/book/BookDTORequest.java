package pl.witoldbrzezinski.libraryapp.book;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookDTORequest {
  @NotNull private String isbn;
  @NotNull private String title;
  @NotNull private String author;
  @Enumerated(EnumType.STRING)
  @NotNull
  private Genre genre;
  @Enumerated(EnumType.STRING)
  @NotNull
  private Status status;
}
