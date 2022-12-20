package pl.witoldbrzezinski.libraryapp.customer;

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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping
  public List<CustomerDTOResponse> getAll() {
    return customerService.getAll();
  }

  @GetMapping("{id}")
  public CustomerDTOResponse getById(@PathVariable Long id) {
    return customerService.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerDTOResponse create(@RequestBody CustomerDTORequest customerDTORequest) {
    return customerService.save(customerDTORequest);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(
      @PathVariable Long id, @Valid @RequestBody CustomerDTORequest customerDTORequest) {
    customerService.update(id, customerDTORequest);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    customerService.delete(id);
  }
}
