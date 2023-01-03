package pl.witoldbrzezinski.libraryapp.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/borrows")
@RequiredArgsConstructor
public class BorrowController {

  private final BorrowService borrowService;

  @GetMapping("/customers/{customerId}")
  public List<BorrowDTOResponse> getCustomerBorrows(@PathVariable Long customerId) {
    return borrowService.getCustomerBorrows(customerId);
  }

  @GetMapping("/books/{bookId}")
  public List<BorrowDTOResponse> getBookBorrows(@PathVariable Long bookId) {
    return borrowService.getBookBorrows(bookId);
  }

  @GetMapping("{id}")
  public BorrowDTOResponse getById(@PathVariable Long id) {
    return borrowService.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BorrowDTOResponse create(@RequestBody BorrowDtoRequest borrowDtoRequest) {
    return borrowService.save(borrowDtoRequest);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable Long id, @Valid @RequestBody BorrowDtoRequest borrowDtoRequest) {
    borrowService.update(id, borrowDtoRequest);
  }

  @PutMapping("/borrow/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void borrow(@PathVariable Long id, @Valid @RequestBody BorrowDtoRequest borrowDtoRequest) {
    borrowService.borrow(id, borrowDtoRequest);
  }

  @PutMapping("/return/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void returnBook(@PathVariable Long id, @Valid @RequestBody BorrowDtoRequest borrowDtoRequest) {
    borrowService.returnBook(id, borrowDtoRequest);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    borrowService.delete(id);
  }
}
