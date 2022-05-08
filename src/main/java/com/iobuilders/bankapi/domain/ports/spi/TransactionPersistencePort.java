package com.iobuilders.bankapi.domain.ports.spi;

import com.iobuilders.bankapi.domain.dtos.TransactionDto;

import java.util.List;
import java.util.Optional;

public interface TransactionPersistencePort {

  TransactionDto saveTransaction(String wallet, TransactionDto transaction);

  Optional<List<TransactionDto>> findAllByWallet(String wallet);
}
