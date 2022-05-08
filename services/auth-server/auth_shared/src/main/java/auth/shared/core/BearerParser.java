package auth.shared.core;

import auth.shared.dto.jwt.BearerDto;
import auth.shared.exceptions.TokenParserException;

public interface BearerParser {
    String BEARER_TOKEN_PREFIX = "Bearer";

    BearerDto parse( String bearerToken ) throws TokenParserException;
}
