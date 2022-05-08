package com.iobuilders.bankapi.infrastructure.mappers;

import com.iobuilders.bankapi.domain.dtos.WalletDto;
import com.iobuilders.bankapi.infrastructure.entities.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WalletsMapper {

  WalletsMapper INSTANCE = Mappers.getMapper(WalletsMapper.class);

  WalletDto walletToWalletDto(WalletEntity wallet);

  WalletEntity walletDtoToWallet(WalletDto walletDto);
}
