package pl.witoldbrzezinski.libraryapp.customer;

public class InvalidPersonalNumberException extends RuntimeException {

  public InvalidPersonalNumberException(String personalNumber) {
    super(String.format("%s is invalid personal number", personalNumber));
  }
}
