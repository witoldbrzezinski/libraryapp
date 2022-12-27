package pl.witoldbrzezinski.libraryapp.borrow;


import java.util.List;

public interface BorrowService {

    List<BorrowDTOResponse> getCustomerBorrows(Long customerId);

    List<BorrowDTOResponse> getBookBorrows(Long bookId);

    BorrowDTOResponse getById(Long id);

    BorrowDTOResponse save(BorrowDtoRequest borrowDtoRequest);


}
