package pl.witoldbrzezinski.libraryapp.security;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final ModelMapper modelMapper;

  UserDTORegisterResponse registerRequestToDTO(UserEntity userEntity) {
    return modelMapper.map(userEntity, UserDTORegisterResponse.class);
  }

  UserEntity registerRequestToEntity(UserDTORegisterRequest userDTORegisterRequest) {
    return modelMapper.map(userDTORegisterRequest, UserEntity.class);
  }
}
