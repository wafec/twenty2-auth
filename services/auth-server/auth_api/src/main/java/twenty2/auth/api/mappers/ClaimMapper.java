package twenty2.auth.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import twenty2.auth.api.entities.Claim;
import twenty2.auth.shared.dto.ClaimCreationDto;
import twenty2.auth.shared.dto.ClaimDto;

@Mapper( componentModel = "spring" )
public interface ClaimMapper {
    @Mapping( target = "id", ignore = true )
    Claim map( ClaimCreationDto claimCreationDto );

    ClaimDto map( Claim claim );
}
