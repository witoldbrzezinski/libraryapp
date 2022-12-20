package pl.witoldbrzezinski.libraryapp.customer;

import pl.witoldbrzezinski.libraryapp.book.BookDTORequest;
import pl.witoldbrzezinski.libraryapp.book.BookDTOResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerDTOResponse> getAll();

    CustomerDTOResponse getById(Long id);

    CustomerDTOResponse save(CustomerDTORequest customerDTORequest);

    CustomerDTOResponse update(Long id, CustomerDTORequest customerDTORequest);

    void delete(Long id);
}
