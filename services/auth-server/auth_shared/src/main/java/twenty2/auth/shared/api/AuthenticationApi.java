package twenty2.auth.shared.api;

import twenty2.auth.shared.dto.UserAndPasswordDto;
import twenty2.auth.shared.exceptions.TokenGenerationException;
import twenty2.auth.shared.exceptions.UserNotFoundException;

public interface AuthenticationApi {
    String authenticate( UserAndPasswordDto userAndPassword )
            throws UserNotFoundException, TokenGenerationException;
}
