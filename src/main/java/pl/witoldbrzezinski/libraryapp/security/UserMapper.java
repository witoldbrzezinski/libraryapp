package pl.witoldbrzezinski.libraryapp.security;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.witoldbrzezinski.libraryapp.book.BookDTORequest;
import pl.witoldbrzezinski.libraryapp.book.BookDTOResponse;
import pl.witoldbrzezinski.libraryapp.book.BookEntity;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final ModelMapper modelMapper;

  UserDTORegisterResponse toDTO(UserEntity userEntity) {
    return modelMapper.map(userEntity, UserDTORegisterResponse.class);
  }

  UserEntity toEntity(UserDTORegisterRequest userDTORegisterRequest) {
    return modelMapper.map(userDTORegisterRequest, UserEntity.class);
  }
}
