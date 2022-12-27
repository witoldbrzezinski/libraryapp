package pl.witoldbrzezinski.libraryapp.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

  private final PersonalNumberValidator personalNumberValidator;

  CustomerDTOResponse toDTO(CustomerEntity customerEntity) {
    return new CustomerDTOResponse(
        customerEntity.getId(),
        customerEntity.getFirstName(),
        customerEntity.getLastName(),
        customerEntity.getGender(),
        customerEntity.getBirthDate(),
        customerEntity.getPersonalNumber(),
        customerEntity.isDeleted(),
        customerEntity.getVersion());
  }

  CustomerEntity toEntity(CustomerDTORequest customerDTORequest) {
    return new CustomerEntity(
        customerDTORequest.getFirstName(),
        customerDTORequest.getLastName(),
        personalNumberValidator.getGender(customerDTORequest.getPersonalNumber()),
        personalNumberValidator.getBirthDate(customerDTORequest.getPersonalNumber()),
        customerDTORequest.getPersonalNumber());
  }
}
