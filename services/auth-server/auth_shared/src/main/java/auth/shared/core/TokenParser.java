package auth.shared.core;

import auth.shared.dto.TokenDto;

public interface TokenParser {
    TokenDto parse( String token );
}
