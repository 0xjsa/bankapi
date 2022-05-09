package com.iobuilders.bankapi.application.controller;

import com.iobuilders.bankapi.domain.dtos.UserDto;
import com.iobuilders.bankapi.domain.ports.api.UserServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

  private final UserServicePort userServicePort;

  public UserController(UserServicePort userServicePort){
    this.userServicePort = userServicePort;
  }

  @PostMapping
  public UserDto createUser(@RequestBody UserDto user){
    log.info(user.toString());
    return userServicePort.createUser(user);
  }

}
