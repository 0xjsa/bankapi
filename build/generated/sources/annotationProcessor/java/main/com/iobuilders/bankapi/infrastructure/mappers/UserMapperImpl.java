package com.iobuilders.bankapi.infrastructure.mappers;

import com.iobuilders.bankapi.domain.dtos.UserDto;
import com.iobuilders.bankapi.domain.dtos.UserDto.UserDtoBuilder;
import com.iobuilders.bankapi.infrastructure.entities.UserEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-08T18:37:15+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.1.jar, environment: Java 17.0.1 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToUserDto(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.dni( user.getDni() );
        userDto.name( user.getName() );
        userDto.surnames( user.getSurnames() );
        userDto.user( user.getUser() );
        userDto.password( user.getPassword() );

        return userDto.build();
    }

    @Override
    public UserEntity userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setDni( userDto.getDni() );
        userEntity.setName( userDto.getName() );
        userEntity.setSurnames( userDto.getSurnames() );
        userEntity.setUser( userDto.getUser() );
        userEntity.setPassword( userDto.getPassword() );

        return userEntity;
    }
}
