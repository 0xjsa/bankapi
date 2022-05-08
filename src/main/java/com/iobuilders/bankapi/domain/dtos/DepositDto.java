package com.iobuilders.bankapi.domain.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class DepositDto {

  private String address;
  private BigDecimal amount;
}
