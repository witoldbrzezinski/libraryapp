package pl.witoldbrzezinski.libraryapp.security;

public class UserEmailAlreadyTakenException extends RuntimeException {

  public UserEmailAlreadyTakenException(String email) {
    super(String.format("User with email %s already exists", email));
  }
}
