package pl.witoldbrzezinski.libraryapp.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.witoldbrzezinski.libraryapp.IntegrationTestDB;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = "/clean-books.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BookIntegrationTest extends IntegrationTestDB {
  @Autowired private BookRepository bookRepository;
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @Test
  @SneakyThrows
  void shouldGetBook() {
    // given
    BookDTORequest bookDTORequest =
        new BookDTORequest(
            "9780131969452", "Design Patterns", "Big Four", Genre.DRAMA, Status.FREE);
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
    BookDTOResponse bookDTOResponse =
        new BookDTOResponse(
            1L,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            Status.FREE,
            "9780131969452-10000",
            false,
            0L);
    bookRepository.save(bookEntity);
    // when
    MvcResult result =
        mockMvc
            .perform(
                get("/books/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(bookDTORequest)))
            .andExpect(status().isOk())
            .andReturn();
    // then
    assertThat(result.getResponse().getContentAsString())
        .isEqualTo(objectMapper.writeValueAsString(bookDTOResponse));
  }

  @Test
  @SneakyThrows
  void shouldGetAllBooks() {
    // given
    BookDTORequest bookDTORequest =
        new BookDTORequest(
            "9780131969452", "Design Patterns", "Big Four", Genre.DRAMA, Status.FREE);
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
    BookDTOResponse bookDTOResponse =
        new BookDTOResponse(
            1L,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            Status.FREE,
            "9780131969452-10000",
            false,
            0L);
    bookRepository.save(bookEntity);
    // when
    MvcResult result =
        mockMvc
            .perform(
                get("/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(bookDTORequest)))
            .andExpect(status().isOk())
            .andReturn();
    // then
    assertThat(result.getResponse().getContentAsString())
        .isEqualTo("[" + objectMapper.writeValueAsString(bookDTOResponse) + "]");
  }

  @Test
  @SneakyThrows
  void shouldSaveBook() {
    // given
    BookDTORequest bookDTORequest =
        new BookDTORequest(
            "9780131969452", "Design Patterns", "Big Four", Genre.DRAMA, Status.FREE);
    // when//then
    mockMvc
        .perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTORequest)))
        .andExpect(status().isCreated())
        .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldUpdateBook() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            1L,
            "9780131969452",
            "Design Patterns",
            "Big Five",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    bookRepository.save(bookEntity);
    BookDTORequest bookDTORequest =
        new BookDTORequest(
            "9780131969452", "Design Patterns", "Big Five", Genre.DRAMA, Status.FREE);
    // when then
    mockMvc
        .perform(
            put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTORequest)))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  @SneakyThrows
  void shouldDeleteBook() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            1L,
            "9780131969452",
            "Design Patterns",
            "Big Five",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    bookRepository.save(bookEntity);
    // when then
    mockMvc.perform(delete("/books/1")).andExpect(status().isNoContent());
  }
}
