package com.iobuilders.bankapi.infrastructure.adapters;

import com.iobuilders.bankapi.domain.dtos.WalletDto;
import com.iobuilders.bankapi.domain.ports.spi.WalletsPersistencePort;
import com.iobuilders.bankapi.infrastructure.mappers.WalletsMapper;
import com.iobuilders.bankapi.infrastructure.repository.UserRepository;
import com.iobuilders.bankapi.infrastructure.repository.WalletsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletsJpaAdapter implements WalletsPersistencePort {

  private final UserRepository userRepository;
  private final WalletsRepository walletsRepository;

  public WalletsJpaAdapter(UserRepository userRepository, WalletsRepository walletsRepository){
    this.userRepository = userRepository;
    this.walletsRepository = walletsRepository;
  }

  @Override
  public Optional<WalletDto> createWallet(String user, WalletDto walletDto) {
    final var userWallet = userRepository.findByUser(user);

    if(userWallet.isPresent()){

      final var wallet = WalletsMapper.INSTANCE.walletDtoToWallet(walletDto);

      wallet.setUser(userWallet.get());
      final var persisted = walletsRepository.save(wallet);

      return Optional.of(WalletsMapper.INSTANCE.walletToWalletDto(persisted));
    }

    return Optional.empty();
  }

  @Override
  public Optional<WalletDto> findById(String address) {
    final var walletEntity = walletsRepository.findById(address);

    return walletEntity.map(WalletsMapper.INSTANCE::walletToWalletDto);
  }

  @Override
  public Optional<WalletDto> saveWallet(WalletDto walletDto) {
    final var walletPersisted = walletsRepository.save(WalletsMapper.INSTANCE.walletDtoToWallet(walletDto));

    return Optional.of(WalletsMapper.INSTANCE.walletToWalletDto(walletPersisted));

  }

}
