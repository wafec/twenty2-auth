package auth.shared.core;

import auth.shared.exceptions.TokenParserException;

public interface TokenParserFactory {
    TokenParser build( String token ) throws TokenParserException;
}
