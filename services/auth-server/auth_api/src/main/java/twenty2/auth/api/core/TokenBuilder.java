package twenty2.auth.api.core;

import twenty2.auth.api.entities.Claim;
import twenty2.auth.api.entities.User;
import twenty2.auth.shared.exceptions.TokenGenerationException;

import java.util.List;

public interface TokenBuilder {
    String generate( User user, List<Claim> claims ) throws TokenGenerationException;
}
