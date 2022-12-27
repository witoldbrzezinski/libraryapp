package pl.witoldbrzezinski.libraryapp.borrow;

public class BorrowNotFoundException extends RuntimeException {
  public BorrowNotFoundException(Long id) {
    super(String.format("Borrow with id %d not found", id));
  }
}
