package com.iobuilders.bankapi.domain.services;

import com.iobuilders.bankapi.domain.dtos.*;
import com.iobuilders.bankapi.domain.ports.api.WalletsServicePort;
import com.iobuilders.bankapi.domain.ports.spi.TransactionPersistencePort;
import com.iobuilders.bankapi.domain.ports.spi.WalletsPersistencePort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WalletsServiceImpl implements WalletsServicePort {

  private final WalletsPersistencePort walletsPersistencePort;
  private final TransactionPersistencePort transactionPersistencePort;

  public WalletsServiceImpl(WalletsPersistencePort walletsPersistencePort,
                            TransactionPersistencePort transactionPersistencePort){
    this.walletsPersistencePort = walletsPersistencePort;
    this.transactionPersistencePort = transactionPersistencePort;
  }

  @Override
  public Optional<WalletDto> createWallet(String user) {
    final var address  = RandomStringUtils.random(35, true, true);
    return walletsPersistencePort.createWallet(user, WalletDto.builder().address(address).balance(BigDecimal.ZERO).build());
  }

  @Override
  @Transactional
  public Optional<TransactionDto> createDeposit(DepositDto deposit) {
    final var wallet = walletsPersistencePort.findById(deposit.getAddress());

    if(wallet.isPresent()){

      walletsPersistencePort.saveWallet(WalletDto.builder()
          .address(wallet.get().getAddress())
          .balance(wallet.get().getBalance().add(deposit.getAmount()))
        .build()
      );

      return Optional.of(transactionPersistencePort.saveTransaction(
        wallet.get().getAddress(),
        TransactionDto.builder()
          .amount(deposit.getAmount())
          .destinationWallet(deposit.getAddress())
          .originWallet("DEPOSITO")
        .build()));
    }

    return Optional.empty();
  }

  @Override
  public Optional<HistoryDto> getHistory(String wallet) {
    final var walletDto = walletsPersistencePort.findById(wallet);

    if(walletDto.isPresent()){

      final var transactions = transactionPersistencePort.findAllByWallet(wallet);

      return Optional.of(HistoryDto.builder()
          .wallet(walletDto.get())
          .transactions(transactions.orElse(List.of()))
        .build()
      );
    }

    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional<TransferResponseDto> transfer(String wallet, TransferDto transferDto) {

    final var walletOrigin = walletsPersistencePort.findById(wallet);

    final var walletDestination = walletsPersistencePort.findById(transferDto.getDestinationWallet());

    if (walletOrigin.isPresent() &&
      walletDestination.isPresent()) {

      if (walletOrigin.get().getBalance().compareTo(transferDto.getAmount()) >= 0) {

        walletsPersistencePort.saveWallet(WalletDto.builder()
          .address(walletOrigin.get().getAddress())
          .balance(walletOrigin.get().getBalance().subtract(transferDto.getAmount()))
          .build()
        );

        transactionPersistencePort.saveTransaction(
          walletOrigin.get().getAddress(),
          TransactionDto.builder()
            .amount(transferDto.getAmount().negate())
            .destinationWallet(walletDestination.get().getAddress())
            .originWallet(walletOrigin.get().getAddress())
            .build());

        walletsPersistencePort.saveWallet(WalletDto.builder()
          .address(walletDestination.get().getAddress())
          .balance(walletDestination.get().getBalance().add(transferDto.getAmount()))
          .build()
        );

        transactionPersistencePort.saveTransaction(
          walletDestination.get().getAddress(),
          TransactionDto.builder()
            .amount(transferDto.getAmount())
            .destinationWallet(walletDestination.get().getAddress())
            .originWallet(walletOrigin.get().getAddress())
            .build());

        return Optional.of(TransferResponseDto.builder()
          .result("OK")
          .build()
        );

      } else {
        log.error("Balance insuficiente");
      }

    }

    return Optional.empty();
  }
}
