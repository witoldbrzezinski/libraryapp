package pl.witoldbrzezinski.libraryapp.customer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerDTOResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private Gender gender;
  private LocalDate birthDate;
  private String personalNumber;
  private boolean isDeleted;
  @EqualsAndHashCode.Exclude private Long version;
}
