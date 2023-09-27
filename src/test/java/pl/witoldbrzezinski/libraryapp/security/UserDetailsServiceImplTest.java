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
  private static final String EMAIL = "username@libraryapp.com";
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
    // given
    UserEntity user =
        new UserEntity(1L, USERNAME, EMAIL, PASSWORD, Set.of(new RoleEntity(Role.ROLE_USER)));
    // when
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
    // then
    assertThat(user.getUsername())
        .isEqualTo(userDetailsService.loadUserByUsername(USERNAME).getUsername());
  }

  @Test
  void shouldThrowExceptionWhenLoadWrongUsername() {
    // given
    UserEntity user =
        new UserEntity(1L, "bad_username", EMAIL, PASSWORD, Set.of(new RoleEntity(Role.ROLE_USER)));

    // when
    when(userRepository.findByUsername(BAD_USERNAME)).thenReturn(Optional.of(user));
    // then
    assertThrowsExactly(
        UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(USERNAME));
  }
}
