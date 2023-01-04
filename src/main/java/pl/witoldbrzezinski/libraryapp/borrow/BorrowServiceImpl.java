package pl.witoldbrzezinski.libraryapp.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.witoldbrzezinski.libraryapp.book.BookEntity;
import pl.witoldbrzezinski.libraryapp.book.BookNotFoundException;
import pl.witoldbrzezinski.libraryapp.book.BookRepository;
import pl.witoldbrzezinski.libraryapp.book.Status;
import pl.witoldbrzezinski.libraryapp.customer.CustomerEntity;
import pl.witoldbrzezinski.libraryapp.customer.CustomerNotFoundException;
import pl.witoldbrzezinski.libraryapp.customer.CustomerRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    BorrowEntity borrowEntity = findBorrow(id);
    return borrowMapper.toDTO(borrowEntity);
  }

  @Override
  public BorrowDTOResponse save(BorrowDtoRequest borrowDtoRequest) {
    CustomerEntity customer = findCustomer(borrowDtoRequest.getCustomerId());
    BookEntity book = findBook(borrowDtoRequest.getBookId());
    BorrowEntity borrow = borrowMapper.toEntity(borrowDtoRequest);
    customer.addBorrow(borrow);
    book.addBorrow(borrow);
    book.setEndOfLastBorrow(LocalDate.now().plusMonths(1));
    borrowRepository.save(borrow);
    return borrowMapper.toDTO(borrow);
  }

  @Transactional
  @Override
  public BorrowDTOResponse update(Long id, BorrowDtoRequest borrowDtoRequest) {
    BorrowEntity borrow = findBorrow(id);
    BookEntity book = findBook(borrowDtoRequest.getBookId());
    borrow.setReturnDate(borrowDtoRequest.getReturnDate());
    book.setEndOfLastBorrow(borrowDtoRequest.getReturnDate());
    return borrowMapper.toDTO(borrow);
  }

  @Transactional
  @Override
  public void delete(Long id) {
    BorrowEntity borrow = findBorrow(id);
    borrow.setDeleted(true);
  }
  @Transactional
  @Override
  public BorrowDTOResponse borrow(Long id, BorrowDtoRequest borrowDtoRequest) {
    BorrowEntity borrow = findBorrow(id);
    BookEntity book = findBook(borrowDtoRequest.getBookId());
    book.setStatus(Status.OCCUPIED);
    return borrowMapper.toDTO(borrow);
  }
  @Transactional
  @Override
  public BorrowDTOResponse returnBook(Long id, BorrowDtoRequest borrowDtoRequest) {
    BorrowEntity borrow = findBorrow(id);
    BookEntity book = findBook(borrowDtoRequest.getBookId());
    book.setStatus(Status.FREE);
    return borrowMapper.toDTO(borrow);
  }

  private BorrowEntity findBorrow(Long id) {
    return borrowRepository.findById(id).orElseThrow(() -> new BorrowNotFoundException(id));
  }

  private CustomerEntity findCustomer(Long customerId) {
    return customerRepository
        .findById(customerId)
        .orElseThrow(() -> new CustomerNotFoundException(customerId));
  }

  private BookEntity findBook(Long bookId) {
    return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
  }
}
