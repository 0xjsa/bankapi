package com.iobuilders.bankapi.application.controller;

import com.iobuilders.bankapi.application.exceptions.ForbiddenException;
import com.iobuilders.bankapi.application.exceptions.ServerErrorException;
import com.iobuilders.bankapi.domain.dtos.*;
import com.iobuilders.bankapi.domain.ports.api.UserServicePort;
import com.iobuilders.bankapi.domain.ports.api.WalletsServicePort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

  public static final String USER_HEADER = "user";
  public static final String PASSWORD_HEADER = "password";

  private final UserServicePort userServicePort;
  private final WalletsServicePort walletsServicePort;

  public WalletController(UserServicePort userServicePort, WalletsServicePort walletsServicePort){
    this.userServicePort = userServicePort;
    this.walletsServicePort = walletsServicePort;
  }

  @PostMapping
  public WalletDto createWallet(@RequestHeader Map<String,String> headers){

    final var login = LoginDto.builder().user(headers.get(USER_HEADER)).password(headers.get(PASSWORD_HEADER)).build();

    checkLogin(login);

    return walletsServicePort.createWallet(login.getUser()).orElseThrow(ServerErrorException::new);

  }

  @PostMapping("/{wallet}/deposit")
  public TransactionDto deposit(@RequestHeader Map<String,String> headers, @RequestBody DepositDto deposit ){

    checkLogin(LoginDto.builder().user(headers.get(USER_HEADER)).password(headers.get(PASSWORD_HEADER)).build());

    return walletsServicePort.createDeposit(deposit).orElseThrow(ServerErrorException::new);

  }

  @GetMapping("/{wallet}/history")
  public HistoryDto getHistoryWallet(@RequestHeader Map<String,String> headers, @PathVariable String wallet){

    checkLogin(LoginDto.builder().user(headers.get(USER_HEADER)).password(headers.get(PASSWORD_HEADER)).build());

    return walletsServicePort.getHistory(wallet).orElseThrow(ServerErrorException::new);
  }

  @PostMapping("/{wallet}/transfer")
  public TransferResponseDto transfer(@RequestHeader Map<String,String> headers, @PathVariable String wallet, @RequestBody TransferDto transferDto){

    checkLogin(LoginDto.builder().user(headers.get(USER_HEADER)).password(headers.get(PASSWORD_HEADER)).build());

    return walletsServicePort.transfer(wallet,transferDto).orElseThrow(ServerErrorException::new);
  }


  public void checkLogin(LoginDto login){

    if(!userServicePort.loginUser(login)){
      throw new ForbiddenException();
    }
  }



}
