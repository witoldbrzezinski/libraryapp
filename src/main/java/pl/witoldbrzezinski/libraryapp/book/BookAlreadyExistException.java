package pl.witoldbrzezinski.libraryapp.book;

public class BookAlreadyExistException extends RuntimeException {
  public BookAlreadyExistException(String isbn) {
    super(String.format("Book with ISBN %s already exists", isbn));
  }
}
