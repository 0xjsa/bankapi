package com.iobuilders.bankapi.domain.ports.spi;

import com.iobuilders.bankapi.domain.dtos.WalletDto;

import java.util.Optional;

public interface WalletsPersistencePort {

  Optional<WalletDto> createWallet(String user, WalletDto wallet);

  Optional<WalletDto> findById(String address);

  Optional<WalletDto> saveWallet(WalletDto walletDto);

}
