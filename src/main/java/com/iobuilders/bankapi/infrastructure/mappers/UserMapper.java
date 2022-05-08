package com.iobuilders.bankapi.infrastructure.mappers;

import com.iobuilders.bankapi.domain.dtos.UserDto;
import com.iobuilders.bankapi.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDto userToUserDto(UserEntity user);

  UserEntity userDtoToUser(UserDto userDto);


}
