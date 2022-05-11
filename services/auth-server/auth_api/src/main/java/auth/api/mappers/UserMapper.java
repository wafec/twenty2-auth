package auth.api.mappers;

import auth.api.entities.User;
import auth.shared.dto.UserSignUpDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper( componentModel = "spring" )
public interface UserMapper {
    User map( UserSignUpDto source );
    void map( UserSignUpDto source, @MappingTarget User destination );
}
