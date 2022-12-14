package pl.witoldbrzezinski.libraryapp.book;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
  @NotNull
  @Min(value=0,message = "Quantity must be positive")
  private Integer quantity;

  private boolean isDeleted;
  private Long version;
}
