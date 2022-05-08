package com.iobuilders.bankapi.domain.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class LoginDto {

  private String user;
  private String password;
}
