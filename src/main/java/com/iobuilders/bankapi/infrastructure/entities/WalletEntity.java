package com.iobuilders.bankapi.infrastructure.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "wallets")
public class WalletEntity {

  @Id
  private String address;
  private BigDecimal balance;

  @OneToOne(mappedBy = "wallet", cascade = CascadeType.ALL)
  private UserEntity user;

  @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
  private List<TransactionEntity> transactions;
}
