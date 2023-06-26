package pl.witoldbrzezinski.libraryapp.security;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

  public static final String ROLE_NOT_FOUND = "Role not found!";
//  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
 // private final JwtUtils jwtUtils;

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
    return userMapper.toDTO(userEntity);
  }
}
