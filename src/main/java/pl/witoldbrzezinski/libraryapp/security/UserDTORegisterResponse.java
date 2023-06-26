package pl.witoldbrzezinski.libraryapp.security;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDTORegisterResponse {

  @NotNull private String username;
  @Email @NotNull private String email;
  @NotNull private String password;
}
