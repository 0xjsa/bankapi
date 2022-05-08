package com.iobuilders.bankapi.domain.ports.api;

import com.iobuilders.bankapi.domain.dtos.LoginDto;
import com.iobuilders.bankapi.domain.dtos.UserDto;

public interface UserServicePort {

  UserDto createUser(UserDto user);

  boolean loginUser(LoginDto login);

}
