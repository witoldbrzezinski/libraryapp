package pl.witoldbrzezinski.libraryapp.security;

import java.util.List;
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
public class UserDTOLoginResponse {

  private String token;
  private String type = "Bearer";
  private String username;
  private String email;
  private List<String> roles;

  public UserDTOLoginResponse(
      String accessToken, String username, String email, List<String> roles) {
    this.token = accessToken;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }
}
