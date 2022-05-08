package com.iobuilders.bankapi.domain.services;

import com.iobuilders.bankapi.domain.dtos.LoginDto;
import com.iobuilders.bankapi.domain.dtos.UserDto;
import com.iobuilders.bankapi.domain.ports.api.UserServicePort;
import com.iobuilders.bankapi.domain.ports.spi.UserPersistencePort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServicePort {

  private final UserPersistencePort userPersistencePort;

  public UserServiceImpl(UserPersistencePort userPersistencePort){
    this.userPersistencePort = userPersistencePort;
  }

  @Override
  public UserDto createUser(UserDto user){
    return userPersistencePort.createUser(user);
  }

  @Override
  public boolean loginUser(LoginDto login) {
    final var user = userPersistencePort.findByUser(login.getUser());

    return user.isPresent() && user.get().getPassword().equals(login.getPassword());
  }

}
