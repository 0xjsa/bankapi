package com.iobuilders.bankapi.infrastructure.mappers;

import com.iobuilders.bankapi.domain.dtos.TransactionDto;
import com.iobuilders.bankapi.infrastructure.entities.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

  TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

  TransactionDto transactionToTransactionDto(TransactionEntity user);

  TransactionEntity transactionDtoToTransaction(TransactionDto transactionDto);
}
