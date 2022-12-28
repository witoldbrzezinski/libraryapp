package pl.witoldbrzezinski.libraryapp.customer;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

  private static final String FIRST_NAME = "JAN";
  private static final String NEW_FIRST_NAME = "JANEK";
  private static final String LAST_NAME = "KOWALSKI";
  private static final String PERSONAL_NUMBER = "89091206218";
  private static final long ID = 1L;

  private final CustomerRepository customerRepository = mock(CustomerRepository.class);
  private final CustomerMapper customerMapper = new CustomerMapper(new PersonalNumberValidator());
  private final CustomerService customerService =
      new CustomerServiceImpl(customerRepository, customerMapper, new PersonalNumberValidator());

  @Test
  void shouldGetCustomerById() {
    // given
    CustomerEntity customerEntity =
        new CustomerEntity(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            PERSONAL_NUMBER,
            false,
            1L);
    // when
    when(customerRepository.findById(ID)).thenReturn(Optional.of(customerEntity));
    // then
    assertThat(customerMapper.toDTO(customerEntity))
        .usingRecursiveComparison()
        .isEqualTo(customerService.getById(ID));
  }

  @Test
  void shouldGetAllCustomers() {
    // given
    CustomerEntity customerEntity =
        new CustomerEntity(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            PERSONAL_NUMBER,
            false,
            1L);
    List<CustomerEntity> customers = List.of(customerEntity);
    CustomerDTOResponse customerDTOResponse =
        new CustomerDTOResponse(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1980, Month.AUGUST, 8),
            PERSONAL_NUMBER,
            false,
            1L);
    // when
    when(customerRepository.findAll()).thenReturn(customers);
    // then
    assertThat(customerService.getAll()).usingRecursiveComparison().isEqualTo(customers);
  }

  @Test
  void shouldSaveCustomer() {
    // given
    CustomerDTORequest customerDTORequest =
        new CustomerDTORequest(FIRST_NAME, LAST_NAME, PERSONAL_NUMBER);
    CustomerEntity customerEntity =
        new CustomerEntity(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            PERSONAL_NUMBER,
            false,
            1L);
    // when
    when(customerRepository.save(any())).thenReturn(customerEntity);
    // then
    assertThat(customerService.save(customerDTORequest))
        .usingRecursiveComparison()
        .ignoringFields("id", "version")
        .isEqualTo(customerMapper.toDTO(customerEntity));
  }

  @Test
  void shouldThrowExceptionWhenSavingCustomerWithInvalidPersonalNumber() {
    // given
    CustomerDTORequest customerDTORequest =
        new CustomerDTORequest(FIRST_NAME, LAST_NAME, PERSONAL_NUMBER + 1);
    CustomerEntity customerEntity =
        new CustomerEntity(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            PERSONAL_NUMBER + 1,
            false,
            1L);
    // when
    when(customerRepository.save(any())).thenReturn(customerEntity);
    // then
    assertThrowsExactly(
        InvalidPersonalNumberException.class, () -> customerService.save(customerDTORequest));
  }

  @Test
  void shouldThrowExceptionWhenSavingAlreadyExistingCustomer() {
    // given
    CustomerDTORequest customerDTORequest =
        new CustomerDTORequest(FIRST_NAME, LAST_NAME, PERSONAL_NUMBER);
    // when
    when(customerRepository.existsByPersonalNumber(PERSONAL_NUMBER)).thenReturn(true);
    // then
    assertThrowsExactly(
        CustomerAlreadyExistException.class, () -> customerService.save(customerDTORequest));
  }

  @Test
  void shouldUpdateCustomer() {
    // given
    CustomerDTORequest customerDTORequest =
        new CustomerDTORequest(FIRST_NAME, LAST_NAME, PERSONAL_NUMBER);
    CustomerEntity customerEntity =
        new CustomerEntity(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            PERSONAL_NUMBER,
            false,
            1L);
    // when
    customerEntity.setFirstName(NEW_FIRST_NAME);
    when(customerRepository.findById(ID)).thenReturn(Optional.of(customerEntity));
    when(customerRepository.save(customerMapper.toEntity(customerDTORequest)))
        .thenReturn(customerEntity);
    // then
    assertThat(customerService.update(ID, customerDTORequest))
        .usingRecursiveComparison()
        .isEqualTo(customerMapper.toDTO(customerEntity));
  }

  @Test
  void shouldThrowExceptionWhenUpdatingCustomerWithInvalidPersonalNumber() {
    // given
    CustomerDTORequest customerDTORequest =
        new CustomerDTORequest(FIRST_NAME, LAST_NAME, PERSONAL_NUMBER + 1);
    CustomerEntity customerEntity =
        new CustomerEntity(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            PERSONAL_NUMBER + 1,
            false,
            1L);
    // when
    customerEntity.setFirstName(NEW_FIRST_NAME);
    when(customerRepository.findById(ID)).thenReturn(Optional.of(customerEntity));
    when(customerRepository.save(customerMapper.toEntity(customerDTORequest)))
        .thenReturn(customerEntity);
    // then
    assertThrowsExactly(
        InvalidPersonalNumberException.class, () -> customerService.update(ID, customerDTORequest));
  }

  @Test
  void shouldDeleteCustomer() {
    // given
    CustomerEntity customerEntity =
        new CustomerEntity(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            PERSONAL_NUMBER + 1,
            false,
            1L); // when
    when(customerRepository.findById(ID)).thenReturn(Optional.of(customerEntity));
    customerService.delete(ID);
    // then
    assertTrue(customerEntity.isDeleted());
  }
}
