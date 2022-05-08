package com.iobuilders.bankapi.infrastructure.mappers;

import com.iobuilders.bankapi.domain.dtos.WalletDto;
import com.iobuilders.bankapi.domain.dtos.WalletDto.WalletDtoBuilder;
import com.iobuilders.bankapi.infrastructure.entities.WalletEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-08T18:37:15+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.1.jar, environment: Java 17.0.1 (Oracle Corporation)"
)
public class WalletsMapperImpl implements WalletsMapper {

    @Override
    public WalletDto walletToWalletDto(WalletEntity wallet) {
        if ( wallet == null ) {
            return null;
        }

        WalletDtoBuilder walletDto = WalletDto.builder();

        walletDto.address( wallet.getAddress() );
        walletDto.balance( wallet.getBalance() );

        return walletDto.build();
    }

    @Override
    public WalletEntity walletDtoToWallet(WalletDto walletDto) {
        if ( walletDto == null ) {
            return null;
        }

        WalletEntity walletEntity = new WalletEntity();

        walletEntity.setAddress( walletDto.getAddress() );
        walletEntity.setBalance( walletDto.getBalance() );

        return walletEntity;
    }
}
