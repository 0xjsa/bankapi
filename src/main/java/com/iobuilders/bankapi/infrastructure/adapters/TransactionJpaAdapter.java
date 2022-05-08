package com.iobuilders.bankapi.infrastructure.adapters;

import com.iobuilders.bankapi.domain.dtos.TransactionDto;
import com.iobuilders.bankapi.domain.ports.spi.TransactionPersistencePort;
import com.iobuilders.bankapi.infrastructure.entities.TransactionEntity;
import com.iobuilders.bankapi.infrastructure.mappers.TransactionMapper;
import com.iobuilders.bankapi.infrastructure.repository.TransactionRepository;
import com.iobuilders.bankapi.infrastructure.repository.WalletsRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionJpaAdapter implements TransactionPersistencePort {

  private final TransactionRepository transactionRepository;

  private final WalletsRepository walletsRepository;

  public TransactionJpaAdapter(TransactionRepository transactionRepository,
                               WalletsRepository walletsRepository){
    this.transactionRepository = transactionRepository;
    this.walletsRepository = walletsRepository;
  }

  @Override
  @Transactional
  public TransactionDto saveTransaction(String wallet, TransactionDto transaction) {

    final var transactionEntity = TransactionMapper.INSTANCE.transactionDtoToTransaction(transaction);

    transactionEntity.setId(
      TransactionEntity.Pk.builder()
        .id(transactionRepository.getMaxIdTransactionWallet(wallet))
        .wallet(wallet)
        .build());

    final var walletEntity = walletsRepository.findById(transaction.getDestinationWallet());

    if(walletEntity.isPresent()){

      if(walletEntity.get().getTransactions()==null){
        walletEntity.get().setTransactions(new ArrayList<>());
      }

      walletEntity.get().getTransactions().add(transactionEntity);

      walletsRepository.save(walletEntity.get());
    }


    return transactionRepository.findById(
      transactionEntity.getId()).map(
        TransactionMapper.INSTANCE::transactionToTransactionDto)
      .orElseThrow(EntityNotFoundException::new);

  }

  @Override
  public Optional<List<TransactionDto>> findAllByWallet(String wallet) {
    final var transactions = transactionRepository.findByIdWallet(wallet);

    return transactions.map(transactionEntities -> transactionEntities.stream().map(TransactionMapper.INSTANCE::transactionToTransactionDto).toList());

  }

}
