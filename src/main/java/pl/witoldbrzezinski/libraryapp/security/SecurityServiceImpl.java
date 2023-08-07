package pl.witoldbrzezinski.libraryapp.security;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

  private static final String ROLE_NOT_FOUND = "Role not found!";
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  @Override
  @Transactional
  public UserDTORegisterResponse registerNewUser(UserDTORegisterRequest userDTORegisterRequest) {
    if (userRepository.existsByUsername(userDTORegisterRequest.getUsername())) {
      throw new UsernameAlreadyExistException(userDTORegisterRequest.getUsername());
    }
    if (userRepository.existsByEmail(userDTORegisterRequest.getEmail())) {
      throw new UserEmailAlreadyTakenException(userDTORegisterRequest.getEmail());
    }
    UserEntity userEntity =
        new UserEntity(
            userDTORegisterRequest.getUsername(),
            userDTORegisterRequest.getEmail(),
            passwordEncoder.encode(userDTORegisterRequest.getPassword()));
    RoleEntity userRole =
        roleRepository
            .findByRole(Role.ROLE_USER)
            .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));
    userEntity.addRole(userRole);
    userRepository.save(userEntity);
    return userMapper.registerRequestToDTO(userEntity);
  }

  @Override
  public UserDTOLoginResponse login(UserDTOLoginRequest userDTOLoginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userDTOLoginRequest.getUsername(), userDTOLoginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());
    return new UserDTOLoginResponse(jwt, userDetails.getUsername(), userDetails.getEmail(), roles);
  }
}
