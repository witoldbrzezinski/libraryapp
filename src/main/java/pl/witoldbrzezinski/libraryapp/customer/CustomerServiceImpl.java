package pl.witoldbrzezinski.libraryapp.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final PersonalNumberValidator personalNumberValidator;

  @Override
  public List<CustomerDTOResponse> getAll() {
    return customerRepository.findAll().stream().map(customerMapper::toDTO).toList();
  }

  @Override
  public CustomerDTOResponse getById(Long id) {
    CustomerEntity customerEntity =
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    return customerMapper.toDTO(customerEntity);
  }

  @Override
  public CustomerDTOResponse save(CustomerDTORequest customerDTORequest) {
    if (customerRepository.existsByPersonalNumber(customerDTORequest.getPersonalNumber())) {
      throw new CustomerAlreadyExistException(customerDTORequest.getPersonalNumber());
    }
    if(!personalNumberValidator.isValid(customerDTORequest.getPersonalNumber())){
      throw new InvalidPersonalNumberException(customerDTORequest.getPersonalNumber());
    }
    CustomerEntity customerEntity = customerMapper.toEntity(customerDTORequest);
    customerRepository.save(customerEntity);
    return customerMapper.toDTO(customerEntity);
  }

  @Override
  @Transactional
  public CustomerDTOResponse update(Long id, CustomerDTORequest customerDTORequest) {
    if(!personalNumberValidator.isValid(customerDTORequest.getPersonalNumber())){
      throw new InvalidPersonalNumberException(customerDTORequest.getPersonalNumber());
    }
    CustomerEntity customerEntity =
            customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    customerEntity.setFirstName(customerDTORequest.getFirstName());
    customerEntity.setLastName(customerDTORequest.getLastName());
    customerEntity.setPersonalNumber(customerDTORequest.getPersonalNumber());
    return customerMapper.toDTO(customerEntity);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    CustomerEntity customerEntity =
            customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    customerEntity.setDeleted(true);
  }
}
