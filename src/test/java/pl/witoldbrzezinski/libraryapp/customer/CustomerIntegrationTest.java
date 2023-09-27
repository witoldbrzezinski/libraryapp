package pl.witoldbrzezinski.libraryapp.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.witoldbrzezinski.libraryapp.IntegrationTestDB;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
@Sql(value = "/clean-customers.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CustomerIntegrationTest extends IntegrationTestDB {
  private static final String FIRST_NAME = "JAN";
  private static final String LAST_NAME = "KOWALSKI";
  private static final String PERSONAL_NUMBER = "89091206218";
  private static final long ID = 1L;
  @Autowired private CustomerRepository customerRepository;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  @SneakyThrows
  void shouldGetCustomer() {
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
    CustomerDTOResponse customerDTOResponse =
        new CustomerDTOResponse(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            PERSONAL_NUMBER,
            false,
            1L);
    customerRepository.save(customerEntity);
    // when
    MvcResult result =
        mockMvc
            .perform(
                get("/customers/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customerDTORequest)))
            .andExpect(status().isOk())
            .andReturn();
    // then
    assertThat(result.getResponse().getContentAsString())
        .isEqualTo(objectMapper.writeValueAsString(customerDTOResponse));
  }

  @Test
  @SneakyThrows
  void shouldGetAllBooks() {
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
    CustomerDTOResponse customerDTOResponse =
        new CustomerDTOResponse(
            ID,
            FIRST_NAME,
            LAST_NAME,
            Gender.MALE,
            LocalDate.of(1989, Month.SEPTEMBER, 12),
            PERSONAL_NUMBER,
            false,
            1L);
    customerRepository.save(customerEntity);
    // when
    MvcResult result =
        mockMvc
            .perform(
                get("/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customerDTORequest)))
            .andExpect(status().isOk())
            .andReturn();
    // then
    assertThat(result.getResponse().getContentAsString())
        .isEqualTo("[" + objectMapper.writeValueAsString(customerDTOResponse) + "]");
  }

  @Test
  @SneakyThrows
  void shouldSaveCustomer() {
    // given
    CustomerDTORequest customerDTORequest =
        new CustomerDTORequest(FIRST_NAME, LAST_NAME, PERSONAL_NUMBER);
    // when//then
    mockMvc
        .perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTORequest)))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldUpdateCustomer() {
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
    customerRepository.save(customerEntity);
    CustomerDTORequest customerDTORequest =
        new CustomerDTORequest(FIRST_NAME, LAST_NAME, PERSONAL_NUMBER);
    // when then
    mockMvc
        .perform(
            put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTORequest)))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldDeleteCustomer() {
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
    customerRepository.save(customerEntity);
    // when then
    mockMvc.perform(delete("/customers/1")).andExpect(status().isNoContent());
  }
}
