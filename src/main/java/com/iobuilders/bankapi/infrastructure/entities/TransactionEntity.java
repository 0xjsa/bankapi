package com.iobuilders.bankapi.infrastructure.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transactions")
public class TransactionEntity {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Embeddable
  public static class Pk implements Serializable {

    private String wallet;

    private Integer id;
  }

  @EmbeddedId
  private Pk id;

  private String originWallet;
  private String destinationWallet;
  private BigDecimal amount;

  @ManyToOne
  @JoinColumn(name = "wallet",
    referencedColumnName = "address",
    insertable = false,
    updatable = false)
  private WalletEntity wallet;
}
