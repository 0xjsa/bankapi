package com.iobuilders.bankapi.domain.ports.spi;

import com.iobuilders.bankapi.domain.dtos.UserDto;

import java.util.Optional;

public interface UserPersistencePort {

  UserDto createUser(UserDto user);

  Optional<UserDto> findByUser(String user);

}
