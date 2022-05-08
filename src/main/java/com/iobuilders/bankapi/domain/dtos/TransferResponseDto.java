package com.iobuilders.bankapi.domain.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class TransferResponseDto {

  private String result;
}
