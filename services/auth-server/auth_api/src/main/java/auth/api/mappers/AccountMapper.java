package auth.api.mappers;

import auth.api.entities.Account;
import auth.shared.dto.AccountDetailsDto;
import auth.shared.dto.AccountDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper( componentModel = "spring" )
public interface AccountMapper {
    Account map( AccountDetailsDto source );
    AccountDto map( Account source );
    @BeanMapping( nullValuePropertyMappingStrategy = IGNORE )
    void map( AccountDetailsDto source, @MappingTarget Account destination );
}
