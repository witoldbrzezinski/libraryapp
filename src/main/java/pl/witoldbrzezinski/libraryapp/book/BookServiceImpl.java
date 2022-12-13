package pl.witoldbrzezinski.libraryapp.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  private final BookMapper bookMapper;

  private final IsbnValidator isbnValidator;

  @Override
  public List<BookDTOResponse> getAll() {
    return bookRepository.findAll().stream().map(bookMapper::toDTO).toList();
  }

  @Override
  public BookDTOResponse getById(Long id) {
    BookEntity bookEntity =
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    return bookMapper.toDTO(bookEntity);
  }

  @Override
  public BookDTOResponse save(BookDTORequest bookDTORequest) {
    if(!isbnValidator.validateIsbn10Or13(bookDTORequest.getIsbn())){
      throw new InvalidIsbnException(bookDTORequest.getIsbn());
    }
    if (bookRepository.existsByIsbn(bookDTORequest.getIsbn())) {
      throw new BookAlreadyExistException(bookDTORequest.getIsbn());
    }
    BookEntity bookEntity = bookMapper.toEntity(bookDTORequest);
    bookRepository.save(bookEntity);
    return bookMapper.toDTO(bookEntity);
  }

  @Override
  @Transactional
  public BookDTOResponse update(Long id, BookDTORequest bookDTORequest) {
    BookEntity bookEntity =
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    bookEntity.setIsbn(bookDTORequest.getIsbn());
    bookEntity.setTitle(bookDTORequest.getTitle());
    bookEntity.setAuthor(bookDTORequest.getAuthor());
    bookEntity.setGenre(bookDTORequest.getGenre());
    return bookMapper.toDTO(bookEntity);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    BookEntity bookEntity =
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    bookEntity.setDeleted(true);
  }
}
