package pl.witoldbrzezinski.libraryapp.borrow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final BorrowMapper borrowMapper;

    @Override
    public List<BorrowDTOResponse> getCustomerBorrows(Long customerId) {
        return borrowRepository.findByCustomer_Id(customerId).stream().map(borrowMapper::toDTO).toList();
    }

}
