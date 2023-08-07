package pl.witoldbrzezinski.libraryapp.security;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecurityServiceImplTest {
  private static final String USERNAME = "username";
  private static final String EMAIL = "username@libraryapp.com";
  private static final String PASSWORD = "password";

  private AuthenticationManager authenticationManager;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private UserMapper userMapper;
  private PasswordEncoder passwordEncoder;
  private JwtUtils jwtUtils;

  private SecurityService securityService;

  @BeforeEach
  void init() {
    authenticationManager = mock(AuthenticationManager.class);
    userRepository = mock(UserRepository.class);
    roleRepository = mock(RoleRepository.class);
    userMapper = new UserMapper(new ModelMapper());
    passwordEncoder = mock(PasswordEncoder.class);
    jwtUtils = mock(JwtUtils.class);
    securityService =
        new SecurityServiceImpl(
            authenticationManager,
            userRepository,
            roleRepository,
            userMapper,
            passwordEncoder,
            jwtUtils);
  }

  @Test
  void shouldRegisterUser() {
    // given
    RoleEntity role = new RoleEntity(Role.ROLE_USER);
    UserEntity user = new UserEntity(1L, USERNAME, EMAIL, PASSWORD, Set.of(role));
    UserDTORegisterRequest userDTORegisterRequest =
        new UserDTORegisterRequest(USERNAME, EMAIL, PASSWORD);
    // when
    when(roleRepository.findByRole(Role.ROLE_USER)).thenReturn(Optional.of(role));
    when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
    when(passwordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
    // then
    assertThat(securityService.registerNewUser(userDTORegisterRequest))
        .usingRecursiveComparison()
        .isEqualTo(userMapper.registerRequestToDTO(user));
  }

  @Test
  void shouldThrowExceptionWhenSavingUserWithTakenUsername(){
    // given
    UserDTORegisterRequest userDTORegisterRequest =
            new UserDTORegisterRequest(USERNAME, EMAIL, PASSWORD);
    // when
    when(userRepository.existsByUsername(USERNAME)).thenReturn(true);
    //then
    assertThrowsExactly(
            UsernameAlreadyExistException.class,()->securityService.registerNewUser(userDTORegisterRequest));
  }

  @Test
  void shouldThrowExceptionWhenSavingUserWithTakenEmail(){
    // given
    UserDTORegisterRequest userDTORegisterRequest =
            new UserDTORegisterRequest(USERNAME, EMAIL, PASSWORD);
    // when
    when(userRepository.existsByEmail(EMAIL)).thenReturn(true);
    //then
    assertThrowsExactly(
            UserEmailAlreadyTakenException.class,()->securityService.registerNewUser(userDTORegisterRequest));
  }
  
}
