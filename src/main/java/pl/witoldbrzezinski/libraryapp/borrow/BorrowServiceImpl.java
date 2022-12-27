package pl.witoldbrzezinski.libraryapp.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.witoldbrzezinski.libraryapp.book.BookEntity;
import pl.witoldbrzezinski.libraryapp.book.BookNotFoundException;
import pl.witoldbrzezinski.libraryapp.book.BookRepository;
import pl.witoldbrzezinski.libraryapp.customer.CustomerEntity;
import pl.witoldbrzezinski.libraryapp.customer.CustomerNotFoundException;
import pl.witoldbrzezinski.libraryapp.customer.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final BorrowMapper borrowMapper;

    @Override
    public List<BorrowDTOResponse> getCustomerBorrows(Long customerId) {
        CustomerEntity customer = findCustomer(customerId);
        return borrowRepository.findByCustomer(customer).stream().map(borrowMapper::toDTO).toList();
    }

    @Override
    public List<BorrowDTOResponse> getBookBorrows(Long bookId) {
        BookEntity book = findBook(bookId);
        return borrowRepository.findByBook(book).stream().map(borrowMapper::toDTO).toList();
    }

    @Override
    public BorrowDTOResponse getById(Long id) {
        BorrowEntity borrowEntity = borrowRepository.findById(id).orElseThrow(()-> new BorrowNotFoundException(id));
        return borrowMapper.toDTO(borrowEntity);
    }


    private CustomerEntity findCustomer(Long customerId){
        return customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(customerId));
    }

    private BookEntity findBook(Long bookId){
        return bookRepository.findById(bookId).orElseThrow(()->new BookNotFoundException(bookId));
    }

}
