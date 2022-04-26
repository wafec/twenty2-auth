package auth.shared.api;

import auth.shared.dto.AuthorizationDto;
import auth.shared.dto.UserAndPasswordDto;
import auth.shared.exceptions.TokenGenerationException;
import auth.shared.exceptions.UserNotFoundException;

public interface AuthenticationApi {
    AuthorizationDto authenticate( UserAndPasswordDto userAndPassword )
            throws UserNotFoundException, TokenGenerationException;
}
