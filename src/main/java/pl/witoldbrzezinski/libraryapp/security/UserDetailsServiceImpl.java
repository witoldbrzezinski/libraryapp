package pl.witoldbrzezinski.libraryapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        String.format("Username %s was not found", username)));
    return UserDetailsImpl.build(user);
  }
}
