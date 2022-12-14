package pl.witoldbrzezinski.libraryapp.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.witoldbrzezinski.libraryapp.utils.HandledExceptionResponse;

import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice
public class BookExceptionHandler {

  @ExceptionHandler(BookNotFoundException.class)
  public ResponseEntity<HandledExceptionResponse> handleBookNotFoundException(
      BookNotFoundException exception) {
    return new ResponseEntity<>(
        new HandledExceptionResponse(LocalDateTime.now(), exception.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BookAlreadyExistException.class)
  public ResponseEntity<HandledExceptionResponse> handleBookAlreadyExists(
      BookAlreadyExistException exception) {
    return new ResponseEntity<>(
        new HandledExceptionResponse(LocalDateTime.now(), exception.getMessage()),
        HttpStatus.CONFLICT);
  }

  @ExceptionHandler(InvalidIsbnException.class)
  public ResponseEntity<HandledExceptionResponse> handleInvalidIsbnException(
      InvalidIsbnException exception) {
    return new ResponseEntity<>(
        new HandledExceptionResponse(LocalDateTime.now(), exception.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<HandledExceptionResponse> handleSQLException(SQLException exception) {
    return new ResponseEntity<>(
        new HandledExceptionResponse(LocalDateTime.now(), exception.getMessage().split("\n")[0]),
        HttpStatus.BAD_REQUEST);
  }
}
