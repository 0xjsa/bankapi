package com.iobuilders.bankapi.infrastructure.mappers;

import com.iobuilders.bankapi.domain.dtos.TransactionDto;
import com.iobuilders.bankapi.domain.dtos.TransactionDto.TransactionDtoBuilder;
import com.iobuilders.bankapi.infrastructure.entities.TransactionEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-08T18:37:15+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.1.jar, environment: Java 17.0.1 (Oracle Corporation)"
)
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionDto transactionToTransactionDto(TransactionEntity user) {
        if ( user == null ) {
            return null;
        }

        TransactionDtoBuilder transactionDto = TransactionDto.builder();

        transactionDto.amount( user.getAmount() );
        transactionDto.originWallet( user.getOriginWallet() );
        transactionDto.destinationWallet( user.getDestinationWallet() );

        return transactionDto.build();
    }

    @Override
    public TransactionEntity transactionDtoToTransaction(TransactionDto transactionDto) {
        if ( transactionDto == null ) {
            return null;
        }

        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setOriginWallet( transactionDto.getOriginWallet() );
        transactionEntity.setDestinationWallet( transactionDto.getDestinationWallet() );
        transactionEntity.setAmount( transactionDto.getAmount() );

        return transactionEntity;
    }
}
