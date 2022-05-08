package com.iobuilders.bankapi.domain.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class TransferDto {

  private String destinationWallet;
  private BigDecimal amount;
}
