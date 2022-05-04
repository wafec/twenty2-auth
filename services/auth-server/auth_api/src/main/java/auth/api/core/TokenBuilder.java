package auth.api.core;

import auth.api.entities.User;
import auth.shared.exceptions.TokenGenerationException;

public interface TokenBuilder {
    String generate( User user ) throws TokenGenerationException;
}
