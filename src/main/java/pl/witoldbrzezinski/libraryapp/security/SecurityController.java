package pl.witoldbrzezinski.libraryapp.security;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

  private final SecurityService securityService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTORegisterResponse create(
      @Valid @RequestBody UserDTORegisterRequest userDTORegisterRequest) {
    return securityService.registerNewUser(userDTORegisterRequest);
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTOLoginResponse login(@Valid @RequestBody UserDTOLoginRequest userDTOLoginRequest) {
    return securityService.login(userDTOLoginRequest);
  }
}
