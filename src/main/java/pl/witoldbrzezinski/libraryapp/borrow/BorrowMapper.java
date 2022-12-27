package pl.witoldbrzezinski.libraryapp.borrow;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BorrowMapper {

  private final ModelMapper modelMapper;

  BorrowDTOResponse toDTO(
      BorrowEntity borrowEntity) {
    return modelMapper.map(borrowEntity,BorrowDTOResponse.class);
  }

  BorrowEntity toEntity(
      BorrowDtoRequest borrowDtoRequest) {
    return modelMapper.map(borrowDtoRequest,BorrowEntity.class);
  }
}
