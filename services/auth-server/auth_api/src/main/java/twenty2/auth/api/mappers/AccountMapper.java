package twenty2.auth.api.mappers;

import twenty2.auth.api.entities.Account;
import twenty2.auth.shared.dto.PersonalInfoDto;
import twenty2.auth.shared.dto.AccountDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper( componentModel = "spring" )
public interface AccountMapper {
    @Mapping( target = "user", ignore = true )
    @Mapping( target = "id", ignore = true )
    Account map( PersonalInfoDto source );

    @Mapping( source = ".", target = "personalInfo" )
    AccountDto map( Account source );

    @Mapping( target = "user", ignore = true )
    @Mapping( target = "id", ignore = true )
    @BeanMapping( nullValuePropertyMappingStrategy = IGNORE )
    void map(PersonalInfoDto source, @MappingTarget Account destination );
}
