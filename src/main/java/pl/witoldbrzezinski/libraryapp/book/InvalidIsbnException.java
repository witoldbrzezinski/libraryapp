package pl.witoldbrzezinski.libraryapp.book;

public class InvalidIsbnException extends RuntimeException {

  public InvalidIsbnException(String isbn) {
    super(String.format("%s is invalid ISBN", isbn));
  }
}
