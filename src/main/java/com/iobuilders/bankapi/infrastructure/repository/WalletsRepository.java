package com.iobuilders.bankapi.infrastructure.repository;

import com.iobuilders.bankapi.infrastructure.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletsRepository extends JpaRepository<WalletEntity, String> {

}
