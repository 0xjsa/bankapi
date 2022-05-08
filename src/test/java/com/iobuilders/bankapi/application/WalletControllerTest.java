package com.iobuilders.bankapi.application;

import com.iobuilders.bankapi.application.controller.WalletController;
import com.iobuilders.bankapi.application.exceptions.ForbiddenException;
import com.iobuilders.bankapi.application.exceptions.ServerErrorException;
import com.iobuilders.bankapi.domain.dtos.*;
import com.iobuilders.bankapi.domain.ports.api.UserServicePort;
import com.iobuilders.bankapi.domain.ports.api.WalletsServicePort;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class WalletControllerTest {

  @MockBean
  UserServicePort userServicePort;

  @MockBean
  WalletsServicePort walletsServicePort;

  WalletController walletController;

  @BeforeEach
  public void before(){
    walletController = new WalletController(userServicePort, walletsServicePort);
  }

  @Test
  void testLogin(){

    Mockito.when(userServicePort.loginUser(any(LoginDto.class))).thenReturn(false);

    Map<String,String> header = new HashMap<>();
    header.put("user","jesusangel93");
    header.put("password", "passworderronea");

    Assertions.assertThrows(ForbiddenException.class, ()->walletController.createWallet(header),"expected createWallet() to throw");
  }

  @Test
  void createWalletTest(){

    Mockito.when(userServicePort.loginUser(any(LoginDto.class))).thenReturn(true);

    Mockito.when(walletsServicePort.createWallet(any(String.class))).thenReturn(
      Optional.ofNullable(
        WalletDto.builder()
          .balance(BigDecimal.ZERO)
          .address(RandomStringUtils.random(35, true, true))
          .build()
      )
    );

    final var result = walletsServicePort.createWallet(any(String.class));

    Assertions.assertNotNull(result);

  }

  @Test
  void createWalletException(){
    Mockito.when(userServicePort.loginUser(any(LoginDto.class))).thenReturn(true);

    Mockito.when(walletsServicePort.createWallet(any(String.class))).thenReturn(Optional.empty());

    Map<String,String> header = new HashMap<>();
    header.put("user","jesusangel93");
    header.put("password", "passwordsegura");

    Assertions.assertThrows(ServerErrorException.class, ()->walletController.createWallet(header),"expected createWallet() to throw");
  }


  @Test
  void depositTest(){

    Map<String,String> header = new HashMap<>();
    header.put("user","jesusangel93");
    header.put("password", "passwordsegura");

    Mockito.when(userServicePort.loginUser(any(LoginDto.class))).thenReturn(true);

    Mockito.when(walletsServicePort.createDeposit(any(DepositDto.class))).thenReturn(Optional.empty());

    Assertions.assertThrows(ServerErrorException.class, ()->
      walletController.deposit(
        header,
        DepositDto.builder()
          .amount(new BigDecimal(10))
          .address(RandomStringUtils.random(35, true, true))
          .build()),
      "expected createDeposit() to throw");
  }

  @Test
  void depositTestOk(){

    Map<String,String> header = new HashMap<>();
    header.put("user","jesusangel93");
    header.put("password", "passwordsegura");

    Mockito.when(userServicePort.loginUser(any(LoginDto.class))).thenReturn(true);

    Mockito.when(walletsServicePort.createDeposit(any(DepositDto.class))).thenReturn(
      Optional.ofNullable(TransactionDto.builder()
          .originWallet(RandomStringUtils.random(35, true, true))
          .destinationWallet(RandomStringUtils.random(35, true, true))
          .amount(new BigDecimal(10))
        .build())
    );

    final var transaction = walletsServicePort.createDeposit(any(DepositDto.class));

    Assertions.assertNotNull(transaction);
  }

  @Test
  void getHistoryWalletTestException(){
    Map<String,String> header = new HashMap<>();
    header.put("user","jesusangel93");
    header.put("password", "passwordsegura");

    Mockito.when(userServicePort.loginUser(any(LoginDto.class))).thenReturn(true);

    Mockito.when(walletsServicePort.getHistory(any(String.class))).thenReturn(Optional.empty());

    Assertions.assertThrows(ServerErrorException.class, ()->
        walletController.getHistoryWallet(
          header,
          any(String.class)),
      "expected getHistory() to throw");
  }

  @Test
  void getHistoryWalletTestOk(){
    Map<String,String> header = new HashMap<>();
    header.put("user","jesusangel93");
    header.put("password", "passwordsegura");

    Mockito.when(userServicePort.loginUser(any(LoginDto.class))).thenReturn(true);

    Mockito.when(walletsServicePort.getHistory(any(String.class))).thenReturn(Optional.of(HistoryDto.builder().build()));

    final var history = walletController.getHistoryWallet(header,RandomStringUtils.random(35, true, true) );

    Assertions.assertNotNull(history);

  }

  @Test
  void transferException(){
    Map<String,String> header = new HashMap<>();
    header.put("user","jesusangel93");
    header.put("password", "passwordsegura");

    Mockito.when(userServicePort.loginUser(any(LoginDto.class))).thenReturn(true);

    Mockito.when(walletsServicePort.transfer(any(String.class),any(TransferDto.class))).thenReturn(Optional.empty());

    Assertions.assertThrows(ServerErrorException.class, ()->
        walletController.transfer(
          header,
          any(String.class),
          TransferDto.builder().build()),
      "expected transfer() to throw");
  }

  @Test
  void transferOk(){
    Map<String,String> header = new HashMap<>();
    header.put("user","jesusangel93");
    header.put("password", "passwordsegura");

    Mockito.when(userServicePort.loginUser(any(LoginDto.class))).thenReturn(true);

    Mockito.when(walletsServicePort.transfer(any(String.class),any(TransferDto.class))).thenReturn(Optional.of(TransferResponseDto.builder().build()));

    final var transferResponse = walletController.transfer(
      header,
      RandomStringUtils.random(35, true, true),
      TransferDto.builder().build());

    Assertions.assertNotNull(transferResponse);
  }





}
