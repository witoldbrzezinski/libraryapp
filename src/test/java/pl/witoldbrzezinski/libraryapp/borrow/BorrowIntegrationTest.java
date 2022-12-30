package pl.witoldbrzezinski.libraryapp.borrow;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.witoldbrzezinski.libraryapp.IntegrationTestDB;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = "/clean-borrows.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BorrowIntegrationTest extends IntegrationTestDB {

  @Autowired private BorrowRepository borrowRepository;

  @Autowired private BookRepository bookRepository;
  @Autowired private CustomerRepository customerRepository;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  private final Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

  private BookEntity bookEntity;
  private CustomerEntity customerEntity;
  private BorrowEntity borrowEntity;
  private BorrowDtoRequest borrowDtoRequest;
  private BorrowDTOResponse borrowDTOResponse;

  @BeforeEach
  void init() {
    bookEntity =
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
    bookRepository.save(bookEntity);
    customerEntity =
        new CustomerEntity(
            1L,
            "Witold",
            "Brzezinski",
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            "89091206218",
            false,
            0L);
    customerRepository.save(customerEntity);
    borrowEntity =
        new BorrowEntity(
            1L, bookEntity, customerEntity, LocalDate.now(fixedClock).plusMonths(1), false, 0L);
    borrowDtoRequest = new BorrowDtoRequest(1L, 1L, LocalDate.now(fixedClock).plusMonths(1));
    borrowDTOResponse =
        new BorrowDTOResponse(
            1L,
            "Design Patterns",
            "9780131969452-10000",
            1L,
            "Witold",
            "Brzezinski",
            LocalDate.now(fixedClock),
            LocalDate.now(fixedClock).plusMonths(1),
            0L);
  }

  @Test
  @SneakyThrows
  void shouldGetCustomerBorrows() {
    // given
    borrowRepository.save(borrowEntity);
    // when
    MvcResult result =
        mockMvc
            .perform(
                get("/borrows/customers/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(borrowDtoRequest)))
            .andExpect(status().isOk())
            .andReturn();
    // then
    assertThat(result.getResponse().getContentAsString())
        .isEqualTo("[" + objectMapper.writeValueAsString(borrowDTOResponse) + "]");
  }

  @Test
  @SneakyThrows
  void shouldGetBookBorrows() {
    // given
    borrowRepository.save(borrowEntity);
    // when
    MvcResult result =
        mockMvc
            .perform(
                get("/borrows/books/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(borrowDtoRequest)))
            .andExpect(status().isOk())
            .andReturn();
    // then
    assertThat(result.getResponse().getContentAsString())
        .isEqualTo("[" + objectMapper.writeValueAsString(borrowDTOResponse) + "]");
  }

  @Test
  @SneakyThrows
  void shouldGetBorrow() {
    // given
    borrowRepository.save(borrowEntity);
    // when
    MvcResult result =
        mockMvc
            .perform(
                get("/borrows/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(borrowDtoRequest)))
            .andExpect(status().isOk())
            .andReturn();
    // then
    assertThat(result.getResponse().getContentAsString())
        .isEqualTo(objectMapper.writeValueAsString(borrowDTOResponse));
  }

  @Test
  @SneakyThrows
  void shouldSaveBorrow() {
    mockMvc
        .perform(
            post("/borrows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(borrowDtoRequest)))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldUpdateBorrow() {
    // given
    borrowRepository.save(borrowEntity);
    // when then
    mockMvc
        .perform(
            put("/borrows/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(borrowDtoRequest)))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldDeleteBorrow() {
    // given
    borrowRepository.save(borrowEntity);
    // when then
    mockMvc.perform(delete("/borrows/1")).andExpect(status().isNoContent());
  }
}
