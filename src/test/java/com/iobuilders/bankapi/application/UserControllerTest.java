package com.iobuilders.bankapi.application;

import com.iobuilders.bankapi.application.controller.UserController;
import com.iobuilders.bankapi.domain.dtos.UserDto;
import com.iobuilders.bankapi.domain.ports.api.UserServicePort;
import com.iobuilders.bankapi.domain.ports.api.WalletsServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserControllerTest {

  @MockBean
  UserServicePort userServicePort;

  UserController userController;

  @BeforeEach
  public void before(){
     userController = new UserController(userServicePort);
  }


  @Test
  void createUserTest(){

    final var userMock = UserDto.builder()
      .dni("77839627Z")
      .surnames("Saorín Saorín")
      .name("Jesús Ángel")
      .user("jesusangel93")
      .password("passwordsegura")
      .build();

    Mockito.when(userServicePort.createUser(any(UserDto.class))).thenReturn(userMock);

    final var user = userController.createUser(userMock);

    assertEquals(userMock, user);
  }


}
