package pl.witoldbrzezinski.libraryapp.customer;

public class CustomerAlreadyExistException extends RuntimeException {
  public CustomerAlreadyExistException(String personalNumber) {
    super(String.format("Customer with personal number %s already exists", personalNumber));
  }
}
