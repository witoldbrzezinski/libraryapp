package pl.witoldbrzezinski.libraryapp.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.witoldbrzezinski.libraryapp.utils.HandledExceptionResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomerExceptionHandler {

  @ExceptionHandler(CustomerNotFoundException.class)
  public ResponseEntity<HandledExceptionResponse> handleCustomerNotFoundException(
      CustomerNotFoundException exception) {
    return new ResponseEntity<>(
        new HandledExceptionResponse(LocalDateTime.now(), exception.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CustomerAlreadyExistException.class)
  public ResponseEntity<HandledExceptionResponse> handleCustomerAlreadyExists(
      CustomerAlreadyExistException exception) {
    return new ResponseEntity<>(
        new HandledExceptionResponse(LocalDateTime.now(), exception.getMessage()),
        HttpStatus.CONFLICT);
  }

  @ExceptionHandler(InvalidPersonalNumberException.class)
  public ResponseEntity<HandledExceptionResponse> handleInvalidPersonalNumberException(
      InvalidPersonalNumberException exception) {
    return new ResponseEntity<>(
        new HandledExceptionResponse(LocalDateTime.now(), exception.getMessage()),
        HttpStatus.BAD_REQUEST);
  }
}
