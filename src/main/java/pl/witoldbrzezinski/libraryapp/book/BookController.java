package pl.witoldbrzezinski.libraryapp.book;

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
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping
  public List<BookDTOResponse> getAll() {
    return bookService.getAll();
  }

  @GetMapping("{id}")
  public BookDTOResponse getById(@PathVariable Long id) {
    return bookService.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookDTOResponse create(@RequestBody BookDTORequest bookDTORequest) {
    return bookService.save(bookDTORequest);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable Long id, @Valid @RequestBody BookDTORequest bookDTORequest) {
    bookService.update(id,bookDTORequest);
  }
  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    bookService.delete(id);
  }
}
