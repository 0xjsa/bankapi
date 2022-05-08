package com.iobuilders.bankapi.infrastructure.repository;

import com.iobuilders.bankapi.infrastructure.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, TransactionEntity.Pk> {

  @Query(
    """
      select COALESCE(MAX(t.id.id)+1,1) from
      TransactionEntity t where
      t.id.wallet = :wallet
    """
  )
  Integer getMaxIdTransactionWallet(String wallet);

  Optional<List<TransactionEntity>> findByIdWallet(String wallet);
}
