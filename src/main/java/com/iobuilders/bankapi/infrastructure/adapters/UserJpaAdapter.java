package com.iobuilders.bankapi.infrastructure.adapters;

import com.iobuilders.bankapi.domain.dtos.UserDto;
import com.iobuilders.bankapi.domain.ports.spi.UserPersistencePort;
import com.iobuilders.bankapi.infrastructure.mappers.UserMapper;
import com.iobuilders.bankapi.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserJpaAdapter implements UserPersistencePort {

  private final UserRepository userRepository;

  public UserJpaAdapter (UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    final var user = UserMapper.INSTANCE.userDtoToUser(userDto);

    final var userSaved = userRepository.save(user);

    return UserMapper.INSTANCE.userToUserDto(userSaved);

  }

  @Override
  public Optional<UserDto> findByUser(String user) {
    final var optUser = userRepository.findByUser(user);

    return optUser.map(UserMapper.INSTANCE::userToUserDto);

  }


}
