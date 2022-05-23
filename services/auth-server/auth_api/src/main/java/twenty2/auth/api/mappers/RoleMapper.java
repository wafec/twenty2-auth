package twenty2.auth.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import twenty2.auth.api.entities.Role;
import twenty2.auth.shared.dto.RoleCreationDto;
import twenty2.auth.shared.dto.RoleDto;

@Mapper( componentModel = "spring" )
public interface RoleMapper {
    @Mapping( target = "id", ignore = true )
    @Mapping( target = "claims", ignore = true )
    Role map( RoleCreationDto roleCreationDto );

    RoleDto map( Role role );
}
