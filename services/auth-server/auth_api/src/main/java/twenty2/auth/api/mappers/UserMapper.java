package twenty2.auth.api.mappers;

import twenty2.auth.api.entities.User;
import twenty2.auth.shared.dto.UserDto;
import twenty2.auth.shared.dto.UserSignUpDto;
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
    @Mapping( target = "roles", ignore = true )
    void map( UserSignUpDto source, @MappingTarget User destination );

    UserDto map( User user );
}
