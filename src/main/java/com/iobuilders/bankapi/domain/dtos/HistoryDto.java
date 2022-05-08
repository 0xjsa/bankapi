package com.iobuilders.bankapi.domain.dtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class HistoryDto {

  private WalletDto wallet;
  private List<TransactionDto> transactions;
}
