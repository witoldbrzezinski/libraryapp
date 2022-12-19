package pl.witoldbrzezinski.libraryapp.book;

import java.util.List;

public interface BookService {

  List<BookDTOResponse> getAll();

  BookDTOResponse getById(Long id);

  BookDTOResponse save(BookDTORequest bookDTORequest);

  BookDTOResponse update(Long id, BookDTORequest bookDTORequest);

  void delete(Long id);
}
