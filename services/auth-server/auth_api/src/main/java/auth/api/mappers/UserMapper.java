package auth.api.mappers;

import auth.api.entities.User;
import auth.shared.dto.UserSignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper( componentModel = "spring" )
public interface UserMapper {
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "claims", ignore = true )
    User map( UserSignUpDto source );

    @Mapping( target = "id", ignore = true )
    @Mapping( target = "claims", ignore = true )
    void map( UserSignUpDto source, @MappingTarget User destination );
}
