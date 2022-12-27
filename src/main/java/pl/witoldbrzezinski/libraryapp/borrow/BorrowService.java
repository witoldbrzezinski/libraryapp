package pl.witoldbrzezinski.libraryapp.borrow;


import java.util.List;

public interface BorrowService {

    List<BorrowDTOResponse> getCustomerBorrows(Long customerId);
}
