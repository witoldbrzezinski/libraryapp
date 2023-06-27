package pl.witoldbrzezinski.libraryapp.security;

public interface SecurityService {

  UserDTORegisterResponse registerNewUser(UserDTORegisterRequest userDTORegisterRequest);

  UserDTOLoginResponse login(UserDTOLoginRequest userDTOLoginRequest);
}
