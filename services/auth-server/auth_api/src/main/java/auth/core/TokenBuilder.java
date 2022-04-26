package auth.core;

import auth.entities.User;
import auth.shared.exceptions.TokenGenerationException;

public interface TokenBuilder {
    String generate( User user ) throws TokenGenerationException;
}
