package pl.witoldbrzezinski.libraryapp.security;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserDetailsServiceImplTest {

  private static final String USERNAME = "username";
  private static final String MAIL = "username@libraryapp.com";
  private static final String PASSWORD = "password";
  private static final String BAD_USERNAME = "bad_username";
  private UserRepository userRepository;
  private UserDetailsServiceImpl userDetailsService;

  @BeforeEach
  void init() {
    userRepository = mock(UserRepository.class);
    userDetailsService = new UserDetailsServiceImpl(userRepository);
  }

  @Test
  void shouldLoadUsernameByUsername() {
    UserEntity user =
        new UserEntity(1L, USERNAME, MAIL, PASSWORD, Set.of(new RoleEntity(Role.ROLE_USER)));
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
    assertThat(user.getUsername()).isEqualTo(userDetailsService.loadUserByUsername(USERNAME).getUsername());
  }

  @Test
  void shouldThrowExceptionWhenLoadWrongUsername() {
    UserEntity user =
            new UserEntity(1L, "bad_username", MAIL, PASSWORD, Set.of(new RoleEntity(Role.ROLE_USER)));
    when(userRepository.findByUsername(BAD_USERNAME)).thenReturn(Optional.of(user));
    assertThrowsExactly(
            UsernameNotFoundException.class,()->userDetailsService.loadUserByUsername(USERNAME));
  }


}
