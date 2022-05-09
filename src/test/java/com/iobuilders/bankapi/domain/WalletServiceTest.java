package com.iobuilders.bankapi.domain;

import com.iobuilders.bankapi.domain.dtos.DepositDto;
import com.iobuilders.bankapi.domain.dtos.TransactionDto;
import com.iobuilders.bankapi.domain.dtos.UserDto;
import com.iobuilders.bankapi.domain.dtos.WalletDto;
import com.iobuilders.bankapi.domain.ports.api.WalletsServicePort;
import com.iobuilders.bankapi.domain.ports.spi.TransactionPersistencePort;
import com.iobuilders.bankapi.domain.ports.spi.WalletsPersistencePort;
import com.iobuilders.bankapi.domain.services.UserServiceImpl;
import com.iobuilders.bankapi.domain.services.WalletsServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class WalletServiceTest {

  @MockBean
  WalletsPersistencePort walletsPersistencePort;

  @MockBean
  TransactionPersistencePort transactionPersistencePort;

  WalletsServicePort walletsServicePort;

  @BeforeEach
  public void before(){
    walletsServicePort = new WalletsServiceImpl(walletsPersistencePort, transactionPersistencePort);
  }

  @Test
  void createWalletTest(){

    final var wallet = RandomStringUtils.random(35, true, true);
    Mockito.when(walletsPersistencePort.createWallet("jesusangel93",WalletDto.builder().address(wallet).balance(BigDecimal.ZERO).build()))
      .thenReturn(Optional.of(WalletDto.builder().address(wallet).balance(BigDecimal.ZERO).build()));

    final var result = walletsServicePort.createWallet("jesusangel93");

    Assertions.assertNotNull(result);

  }

  @Test
  void createDepositTest(){

    final var wallet = RandomStringUtils.random(35, true, true);

    final var depositDto = TransactionDto.builder()
      .amount(BigDecimal.TEN)
      .destinationWallet(wallet)
      .originWallet("DEPOSITO")
      .build();

    Mockito.when(walletsPersistencePort.findById(wallet)).thenReturn(
      Optional.of(
        WalletDto.builder()
          .address(wallet)
          .balance(BigDecimal.ZERO)
          .build()));

    Mockito.when(walletsPersistencePort.saveWallet(WalletDto.builder()
        .address(wallet)
        .balance(BigDecimal.TEN)
        .build()
      )
    ).thenReturn(Optional.of(WalletDto.builder().address(wallet).balance(BigDecimal.TEN).build()));

    Mockito.when(transactionPersistencePort.saveTransaction(wallet,depositDto)).thenReturn(depositDto);

    final var result =  walletsServicePort.createDeposit(
      DepositDto.builder()
        .address(wallet)
        .amount(BigDecimal.TEN)
        .build());

    Assertions.assertNotNull(result);
    Assertions.assertEquals(BigDecimal.TEN, result.get().getAmount());
  }

}
