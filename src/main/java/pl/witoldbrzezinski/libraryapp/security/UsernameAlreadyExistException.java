package pl.witoldbrzezinski.libraryapp.security;

public class UsernameAlreadyExistException extends RuntimeException {

  public UsernameAlreadyExistException(String username) {
    super(String.format("User with name %s already exists", username));
  }
}
