package auth.api.mappers;

import auth.api.entities.User;
import auth.shared.dto.UserSignUpDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User map( UserSignUpDto source );
    void map( UserSignUpDto source, User destination );
}
