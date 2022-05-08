package com.iobuilders.bankapi.domain.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UserDto {

  private String dni;
  private String name;
  private String surnames;
  private String user;
  private String password;
}