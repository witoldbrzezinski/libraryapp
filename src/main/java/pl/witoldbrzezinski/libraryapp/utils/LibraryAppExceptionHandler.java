package pl.witoldbrzezinski.libraryapp.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;

@ControllerAdvice
public class LibraryAppExceptionHandler {

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<HandledExceptionResponse> handleSQLException(SQLException exception) {
    return new ResponseEntity<>(
        new HandledExceptionResponse(LocalDateTime.now(), exception.getMessage().split("\n")[0]),
        HttpStatus.BAD_REQUEST);
  }
}
