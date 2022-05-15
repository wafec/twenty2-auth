package twenty2.auth.shared.core;

import twenty2.auth.shared.dto.jwt.BearerDto;
import twenty2.auth.shared.exceptions.TokenParserException;
import org.springframework.stereotype.Component;

@Component
public class BearerParserImpl implements BearerParser {
    @Override
    public BearerDto parse( String bearerToken ) throws TokenParserException {
        try {
            if (bearerToken.startsWith( BEARER_TOKEN_PREFIX ) ) {
                bearerToken = bearerToken.split("\\s")[1].strip();
            }
            String[] bearerSplit = bearerToken.split( "\\." );
            return new BearerDto( bearerToken, bearerSplit[0], bearerSplit[1], bearerSplit[2] );
        } catch( NullPointerException | IndexOutOfBoundsException exc ) {
            throw new TokenParserException( exc );
        }
    }
}
