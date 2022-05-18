package twenty2.auth.shared.core;

import twenty2.auth.shared.dto.jwt.BearerDto;
import twenty2.auth.shared.exceptions.TokenParserException;

public interface BearerParser {
    String BEARER_TOKEN_PREFIX = "Bearer";

    BearerDto parse(String bearerToken ) throws TokenParserException;
}
