package pl.witoldbrzezinski.libraryapp.borrow;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.witoldbrzezinski.libraryapp.book.BookEntity;
import pl.witoldbrzezinski.libraryapp.book.BookRepository;
import pl.witoldbrzezinski.libraryapp.book.Genre;
import pl.witoldbrzezinski.libraryapp.book.Status;
import pl.witoldbrzezinski.libraryapp.customer.CustomerEntity;
import pl.witoldbrzezinski.libraryapp.customer.CustomerRepository;
import pl.witoldbrzezinski.libraryapp.customer.Gender;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BorrowServiceImplTest {

  private final BorrowRepository borrowRepository = mock(BorrowRepository.class);
  private final BookRepository bookRepository = mock(BookRepository.class);
  private final CustomerRepository customerRepository = mock(CustomerRepository.class);
  private ModelMapper modelMapper = mock(ModelMapper.class);
  private final BorrowMapper borrowMapper = new BorrowMapper(modelMapper);
  private final Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
  private final BorrowService borrowService =
      new BorrowServiceImpl(borrowRepository, bookRepository, customerRepository, borrowMapper);

  @Test
  void shouldGetCustomerBorrows() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            1L,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    CustomerEntity customerEntity =
        new CustomerEntity(
            1L,
            "Witold",
            "Brzezinski",
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            "89091206218",
            false,
            1L);
    BorrowEntity borrowEntity =
        new BorrowEntity(
            1L, bookEntity, customerEntity, LocalDate.now(fixedClock).plusMonths(1), false, 0L);
    BorrowDTOResponse borrowDTOResponse =
        new BorrowDTOResponse(
            1L,
            "Design Patterns",
            "9780131969452-10000",
            1L,
            "Witold",
            "Brzezinski",
            LocalDate.now(fixedClock),
            LocalDate.now(fixedClock).plusMonths(1),
            1L);
    List<BorrowEntity> borrows = List.of(borrowEntity);
    List<BorrowDTOResponse> borrowDTOResponses = List.of(borrowDTOResponse);
    // when
    when(customerRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
    when(borrowRepository.findByCustomer(customerEntity)).thenReturn(borrows);
    when(borrowMapper.toDTO(borrowEntity)).thenReturn(borrowDTOResponse);
    // then
    assertThat(borrowService.getCustomerBorrows(customerEntity.getId()))
        .usingRecursiveComparison()
        .isEqualTo(borrowDTOResponses);
  }

  @Test
  void shouldGetBookBorrows() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            1L,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    CustomerEntity customerEntity =
        new CustomerEntity(
            1L,
            "Witold",
            "Brzezinski",
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            "89091206218",
            false,
            1L);
    BorrowEntity borrowEntity =
        new BorrowEntity(
            1L, bookEntity, customerEntity, LocalDate.now(fixedClock).plusMonths(1), false, 0L);
    BorrowDTOResponse borrowDTOResponse =
        new BorrowDTOResponse(
            1L,
            "Design Patterns",
            "9780131969452-10000",
            1L,
            "Witold",
            "Brzezinski",
            LocalDate.now(fixedClock),
            LocalDate.now(fixedClock).plusMonths(1),
            1L);
    List<BorrowEntity> borrows = List.of(borrowEntity);
    List<BorrowDTOResponse> borrowDTOResponses = List.of(borrowDTOResponse);
    // when
    when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
    when(borrowRepository.findByBook(bookEntity)).thenReturn(borrows);
    when(borrowMapper.toDTO(borrowEntity)).thenReturn(borrowDTOResponse);
    // then
    assertThat(borrowService.getBookBorrows(bookEntity.getId()))
        .usingRecursiveComparison()
        .isEqualTo(borrowDTOResponses);
  }

  @Test
  void shouldGetBorrowById() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            1L,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    CustomerEntity customerEntity =
        new CustomerEntity(
            1L,
            "Witold",
            "Brzezinski",
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            "89091206218",
            false,
            1L);
    BorrowEntity borrowEntity =
        new BorrowEntity(
            1L, bookEntity, customerEntity, LocalDate.now(fixedClock).plusMonths(1), false, 0L);
    // when
    when(borrowRepository.findById(1L)).thenReturn(Optional.of(borrowEntity));
    // then
    assertThat(borrowMapper.toDTO(borrowEntity))
        .usingRecursiveComparison()
        .isEqualTo(borrowService.getById(1L));
  }

  @Test
  void shouldSaveBorrow() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            1L,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    CustomerEntity customerEntity =
        new CustomerEntity(
            1L,
            "Witold",
            "Brzezinski",
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            "89091206218",
            false,
            1L);
    BorrowEntity borrowEntity =
        new BorrowEntity(
            1L, bookEntity, customerEntity, LocalDate.now(fixedClock).plusMonths(1), false, 0L);
    BorrowDtoRequest borrowDtoRequest =
        new BorrowDtoRequest(1L, 1L, LocalDate.now(fixedClock).plusMonths(1));
    BorrowDTOResponse borrowDTOResponse =
        new BorrowDTOResponse(
            1L,
            "Design Patterns",
            "9780131969452-10000",
            1L,
            "Witold",
            "Brzezinski",
            LocalDate.now(fixedClock),
            LocalDate.now(fixedClock).plusMonths(1),
            1L);
    // when
    when(customerRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
    when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
    when(borrowRepository.save(any(BorrowEntity.class))).thenReturn(borrowEntity);
    when(borrowMapper.toEntity(borrowDtoRequest)).thenReturn(borrowEntity);
    when(borrowMapper.toDTO(borrowEntity)).thenReturn(borrowDTOResponse);
    // then
    assertThat(borrowService.save(borrowDtoRequest))
        .usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(borrowDTOResponse);
  }

  @Test
  void shouldUpdateBorrow() {
    // given
    BookEntity bookEntity =
            new BookEntity(
                    1L,
                    "9780131969452",
                    "Design Patterns",
                    "Big Four",
                    Genre.DRAMA,
                    "9780131969452-10000",
                    Status.FREE,
                    false,
                    0L);
    CustomerEntity customerEntity =
        new CustomerEntity(
            1L,
            "Witold",
            "Brzezinski",
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            "89091206218",
            false,
            1L);
    BorrowEntity borrowEntity =
        new BorrowEntity(
            1L, bookEntity, customerEntity, LocalDate.now(fixedClock).plusMonths(1), false, 0L);
    BorrowDtoRequest borrowDtoRequest =
        new BorrowDtoRequest(bookEntity.getId(), customerEntity.getId(), LocalDate.now(fixedClock).plusMonths(1));
    BorrowDTOResponse borrowDTOResponse =
        new BorrowDTOResponse(
            1L,
            "Design Patterns",
            "9780131969452-10000",
            1L,
            "Witold",
            "Brzezinski",
            LocalDate.now(fixedClock),
            LocalDate.now(fixedClock).plusMonths(1),
            1L);
    borrowEntity.setReturnDate(LocalDate.now(fixedClock).plusMonths(2));
    // when
    when(borrowRepository.findById(1L)).thenReturn(Optional.of(borrowEntity));
    when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
    when(borrowMapper.toEntity(borrowDtoRequest)).thenReturn(borrowEntity);
    when(borrowMapper.toDTO(borrowEntity)).thenReturn(borrowDTOResponse);
    // then
    assertThat(borrowService.update(1L, borrowDtoRequest))
        .usingRecursiveComparison()
        .isEqualTo(borrowDTOResponse);
  }

  @Test
  void shouldDeleteBorrow() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            1L,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    CustomerEntity customerEntity =
        new CustomerEntity(
            1L,
            "Witold",
            "Brzezinski",
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            "89091206218",
            false,
            1L);
    BorrowEntity borrowEntity =
        new BorrowEntity(
            1L, bookEntity, customerEntity, LocalDate.now(fixedClock).plusMonths(1), false, 0L);
    // when
    when(borrowRepository.findById(1L)).thenReturn(Optional.of(borrowEntity));
    borrowService.delete(1L);
    // then
    assertTrue(borrowEntity.isDeleted());
  }

  @Test
  void shouldBorrowBook(){
    //given
    BookEntity bookEntity =
            new BookEntity(
                    1L,
                    "9780131969452",
                    "Design Patterns",
                    "Big Four",
                    Genre.DRAMA,
                    "9780131969452-10000",
                    Status.FREE,
                    false,
                    0L);
    CustomerEntity customerEntity =
            new CustomerEntity(
                    1L,
                    "Witold",
                    "Brzezinski",
                    Gender.MALE,
                    LocalDate.of(1989, Month.SEPTEMBER, 12),
                    "89091206218",
                    false,
                    1L);
    BorrowEntity borrowEntity =
            new BorrowEntity(
                    1L, bookEntity, customerEntity, LocalDate.now(fixedClock).plusMonths(1), false, 0L);
    BorrowDtoRequest borrowDtoRequest =
            new BorrowDtoRequest(bookEntity.getId(), customerEntity.getId(), LocalDate.now(fixedClock).plusMonths(1));
    BorrowDTOResponse borrowDTOResponse =
            new BorrowDTOResponse(
                    1L,
                    "Design Patterns",
                    "9780131969452-10000",
                    1L,
                    "Witold",
                    "Brzezinski",
                    LocalDate.now(fixedClock),
                    LocalDate.now(fixedClock).plusMonths(1),
                    1L);
    // when
    when(borrowRepository.findById(1L)).thenReturn(Optional.of(borrowEntity));
    when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
    when(borrowMapper.toEntity(borrowDtoRequest)).thenReturn(borrowEntity);
    when(borrowMapper.toDTO(borrowEntity)).thenReturn(borrowDTOResponse);
    borrowService.borrow(borrowEntity.getId(),borrowDtoRequest);
    //then
    assertEquals(bookEntity.getStatus(),Status.OCCUPIED);
  }

  @Test
  void shouldChangeStatusWhenReturnBook(){
    //given
    BookEntity bookEntity =
            new BookEntity(
                    1L,
                    "9780131969452",
                    "Design Patterns",
                    "Big Four",
                    Genre.DRAMA,
                    "9780131969452-10000",
                    Status.OCCUPIED,
                    false,
                    0L);
    CustomerEntity customerEntity =
            new CustomerEntity(
                    1L,
                    "Witold",
                    "Brzezinski",
                    Gender.MALE,
                    LocalDate.of(1989, Month.SEPTEMBER, 12),
                    "89091206218",
                    false,
                    1L);
    BorrowEntity borrowEntity =
            new BorrowEntity(
                    1L, bookEntity, customerEntity, LocalDate.now(fixedClock).plusMonths(1), false, 0L);
    BorrowDtoRequest borrowDtoRequest =
            new BorrowDtoRequest(bookEntity.getId(), customerEntity.getId(), LocalDate.now(fixedClock).plusMonths(1));
    BorrowDTOResponse borrowDTOResponse =
            new BorrowDTOResponse(
                    1L,
                    "Design Patterns",
                    "9780131969452-10000",
                    1L,
                    "Witold",
                    "Brzezinski",
                    LocalDate.now(fixedClock),
                    LocalDate.now(fixedClock).plusMonths(1),
                    1L);
    // when
    when(borrowRepository.findById(1L)).thenReturn(Optional.of(borrowEntity));
    when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
    when(borrowMapper.toEntity(borrowDtoRequest)).thenReturn(borrowEntity);
    when(borrowMapper.toDTO(borrowEntity)).thenReturn(borrowDTOResponse);
    borrowService.returnBook(borrowEntity.getId(),borrowDtoRequest);
    //then
    assertEquals(bookEntity.getStatus(),Status.FREE);
  }
}
