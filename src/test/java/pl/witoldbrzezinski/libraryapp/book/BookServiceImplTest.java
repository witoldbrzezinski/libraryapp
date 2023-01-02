package pl.witoldbrzezinski.libraryapp.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookServiceImplTest {

  private static final long ID = 1L;
  private BookRepository bookRepository;

  private BookMapper bookMapper;

  private BookService bookService;

  private Clock clock;

  @BeforeEach
  void init() {
    bookRepository = mock(BookRepository.class);
    bookMapper = new BookMapper(new ModelMapper());
    IsbnValidator isbnValidator = new IsbnValidator();
    clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    bookService = new BookServiceImpl(bookRepository, bookMapper, isbnValidator, clock);
  }

  @Test
  void shouldGetListOfAllBooks() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            ID,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            LocalDate.now(clock).plusMonths(1),
            false,
            0L);
    List<BookEntity> books = List.of(bookEntity);
    BookDTOResponse bookDTOResponse =
        new BookDTOResponse(
            ID,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            Status.FREE,
            "9780131969452-10000",
            LocalDate.now(clock).plusMonths(1),
            false,
            0L);
    // when
    when(bookRepository.findAll()).thenReturn(books);
    // then
    assertThat(bookDTOResponse).usingRecursiveComparison().isEqualTo(bookService.getAll().get(0));
  }

  @Test
  void shouldGetBookById() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            ID,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    // when
    when(bookRepository.findById(ID)).thenReturn(Optional.of(bookEntity));
    // then
    assertThat(bookMapper.toDTO(bookEntity))
        .usingRecursiveComparison()
        .isEqualTo(bookService.getById(ID));
  }

  @Test
  void shouldSaveBook() {
    // given
    BookDTORequest bookDTORequest =
        new BookDTORequest(
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            Status.FREE,
            LocalDate.now(clock).plusMonths(1));
    BookEntity bookEntity =
        new BookEntity(
            ID,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            LocalDate.now(clock).plusMonths(1),
            false,
            0L);
    // when
    when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);
    // then
    assertThat(bookService.save(bookDTORequest))
        .usingRecursiveComparison()
        .ignoringFields("id", "version", "index")
        .isEqualTo(bookMapper.toDTO(bookEntity));
  }

  @Test
  void shouldThrowExceptionWhenSavingWithInvalidIsbn() {
    BookDTORequest bookDTORequest =
        new BookDTORequest(
            "97801", "Design Patterns", "Big Four", Genre.DRAMA, Status.FREE, LocalDate.now(clock).plusMonths(1));
    BookEntity bookEntity =
        new BookEntity(
            ID,
            "97801",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    // when
    when(bookRepository.save(bookMapper.toEntity(bookDTORequest))).thenReturn(bookEntity);
    // then
    assertThrowsExactly(InvalidIsbnException.class, () -> bookService.save(bookDTORequest));
  }

  @Test
  void shouldNotSaveAlreadyExistingBook() {
    // given
    BookDTORequest bookDTORequest =
        new BookDTORequest(
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            Status.FREE,
            LocalDate.now(clock).plusMonths(1));
    when(bookRepository.existsByIsbn("9780131969452")).thenReturn(true);
    // when then
    assertThrowsExactly(BookAlreadyExistException.class, () -> bookService.save(bookDTORequest));
  }

  @Test
  void shouldUpdateBook() {
    // given
    BookDTORequest bookDTORequest =
        new BookDTORequest(
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            Status.FREE,
            LocalDate.now(clock));
    BookEntity bookEntity =
        new BookEntity(
            ID,
            "9780131969452",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    // when
    bookEntity.setAuthor("Big Five");
    when(bookRepository.findById(ID)).thenReturn(Optional.of(bookEntity));
    when(bookRepository.save(bookMapper.toEntity(bookDTORequest))).thenReturn(bookEntity);
    // then
    assertThat(bookService.update(ID, bookDTORequest))
        .usingRecursiveComparison()
        .isEqualTo(bookMapper.toDTO(bookEntity));
  }

  @Test
  void shouldNotUpdateBookWhenIsbnIsIncorrect() {
    // given
    BookDTORequest bookDTORequest =
        new BookDTORequest(
            "9780131",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            Status.FREE,
            LocalDate.now(clock).plusMonths(1));
    BookEntity bookEntity =
        new BookEntity(
            ID,
            "9780131",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    // when
    bookEntity.setAuthor("Big Five");
    when(bookRepository.findById(ID)).thenReturn(Optional.of(bookEntity));
    when(bookRepository.save(bookMapper.toEntity(bookDTORequest))).thenReturn(bookEntity);
    assertThrowsExactly(InvalidIsbnException.class, () -> bookService.update(ID, bookDTORequest));
    // then
  }

  @Test
  void shouldDeleteBook() {
    // given
    BookEntity bookEntity =
        new BookEntity(
            ID,
            "9780131",
            "Design Patterns",
            "Big Four",
            Genre.DRAMA,
            "9780131969452-10000",
            Status.FREE,
            false,
            0L);
    // when
    when(bookRepository.findById(ID)).thenReturn(Optional.of(bookEntity));
    bookService.delete(ID);
    // then
    assertTrue(bookEntity.isDeleted());
  }
}
