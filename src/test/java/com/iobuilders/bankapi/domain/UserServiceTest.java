package com.iobuilders.bankapi.domain;

import com.iobuilders.bankapi.domain.dtos.LoginDto;
import com.iobuilders.bankapi.domain.dtos.UserDto;
import com.iobuilders.bankapi.domain.ports.api.UserServicePort;
import com.iobuilders.bankapi.domain.ports.spi.UserPersistencePort;
import com.iobuilders.bankapi.domain.services.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceTest {

  @MockBean
  UserPersistencePort userPersistencePort;

  UserServicePort userServicePort;


  @BeforeEach
  public void before(){
    userServicePort = new UserServiceImpl(userPersistencePort);
  }

  @Test
  void createUserTest(){
    Mockito.when(userServicePort.createUser(any(UserDto.class))).thenReturn(UserDto.builder().build());

    final var userPersited = userServicePort.createUser(
      UserDto.builder()
        .dni("77839627Z")
        .name("Jesús Ángel")
        .surnames("Saorín Saorín")
        .user("jesusangel93")
        .password("passwordsegura")
        .build());

    Assertions.assertNotNull(userPersited);
  }

  @Test
  void loginUserTest(){
    Mockito.when(userPersistencePort.findByUser(any(String.class))).thenReturn(Optional.of(UserDto.builder().user("jesusangel93").password("passwordsegura").build()));

    final var loginSuccesfull = userServicePort.loginUser(LoginDto.builder().user("jesusangel93").password("passwordsegura").build());

    Assertions.assertEquals(true, loginSuccesfull);

  }

}
