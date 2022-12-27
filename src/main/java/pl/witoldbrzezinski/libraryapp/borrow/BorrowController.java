package pl.witoldbrzezinski.libraryapp.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @GetMapping("/customers/{customerId}")
    public List<BorrowDTOResponse> getCustomerBorrows(@PathVariable Long customerId){
        return borrowService.getCustomerBorrows(customerId);
    }

    @GetMapping("/books/{bookId}")
    public List<BorrowDTOResponse> getBookBorrows(@PathVariable Long bookId){
        return borrowService.getBookBorrows(bookId);
    }

    @GetMapping("/{id}")
    public BorrowDTOResponse getById(@PathVariable Long id){
        return borrowService.getById(id);
    }
}
