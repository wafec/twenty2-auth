package auth.shared.core;

import auth.shared.dto.jwt.BearerDto;
import auth.shared.exceptions.TokenParserException;
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
