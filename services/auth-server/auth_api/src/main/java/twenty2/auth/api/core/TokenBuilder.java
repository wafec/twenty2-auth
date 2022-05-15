package twenty2.auth.api.core;

import twenty2.auth.api.entities.User;
import twenty2.auth.shared.exceptions.TokenGenerationException;

public interface TokenBuilder {
    String generate( User user ) throws TokenGenerationException;
}
