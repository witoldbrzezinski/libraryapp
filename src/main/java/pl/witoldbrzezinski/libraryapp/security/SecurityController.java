package pl.witoldbrzezinski.libraryapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.witoldbrzezinski.libraryapp.book.BookDTORequest;
import pl.witoldbrzezinski.libraryapp.book.BookDTOResponse;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTORegisterResponse create(@Valid @RequestBody UserDTORegisterRequest userDTORegisterRequest) {
        return securityService.registerNewUser(userDTORegisterRequest);
    }
}
