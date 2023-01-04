package pl.witoldbrzezinski.libraryapp.borrow;

import java.util.List;

public interface BorrowService {

  List<BorrowDTOResponse> getCustomerBorrows(Long customerId);

  List<BorrowDTOResponse> getBookBorrows(Long bookId);

  BorrowDTOResponse getById(Long id);

  BorrowDTOResponse save(BorrowDtoRequest borrowDtoRequest);

  BorrowDTOResponse update(Long id, BorrowDtoRequest borrowDtoRequest);

  void delete(Long id);
  BorrowDTOResponse borrow(Long id, BorrowDtoRequest borrowDtoRequest);
  BorrowDTOResponse returnBook(Long id, BorrowDtoRequest borrowDtoRequest);

}
