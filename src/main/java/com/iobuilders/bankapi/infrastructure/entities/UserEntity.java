package com.iobuilders.bankapi.infrastructure.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  private String dni;
  private String name;
  private String surnames;
  private String user;
  private String password;

  @OneToOne
  @JoinColumn(name = "wallet", referencedColumnName = "address")
  private WalletEntity wallet;
}
