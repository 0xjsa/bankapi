package com.iobuilders.bankapi.domain.ports.api;

import com.iobuilders.bankapi.domain.dtos.*;

import java.util.Optional;

public interface WalletsServicePort {
  Optional<WalletDto> createWallet(String user);

  Optional<TransactionDto> createDeposit(DepositDto deposit);

  Optional<HistoryDto> getHistory(String wallet);

  Optional<TransferResponseDto> transfer(String wallet, TransferDto transferDto);
}
