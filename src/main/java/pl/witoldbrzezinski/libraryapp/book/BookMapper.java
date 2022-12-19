package pl.witoldbrzezinski.libraryapp.book;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

  private final ModelMapper modelMapper;

  BookDTOResponse toDTO(BookEntity bookEntity) {
    return modelMapper.map(bookEntity, BookDTOResponse.class);
  }

  BookEntity toEntity(BookDTORequest bookDTORequest) {
    return modelMapper.map(bookDTORequest, BookEntity.class);
  }
}
