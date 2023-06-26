package pl.witoldbrzezinski.libraryapp.security;



import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDTORegisterRequest {

  @NotNull private String username;
  @Email @NotNull private String email;
  @NotNull private String password;
}
