package twenty2.auth.shared.core;

import twenty2.auth.shared.exceptions.TokenParserException;

public interface TokenParserFactory {
    TokenParser build( String token ) throws TokenParserException;
}
