package com.iobuilders.bankapi.domain.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class TransactionDto {

  private BigDecimal amount;
  private String originWallet;
  private String destinationWallet;
}
