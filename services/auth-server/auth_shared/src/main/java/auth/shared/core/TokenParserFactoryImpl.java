package auth.shared.core;

import auth.shared.exceptions.TokenParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenParserFactoryImpl implements TokenParserFactory {
    private final ObjectHashParserFactory objectHashParserFactory;
    private final SignatureValidator signatureValidator;
    private final BearerParser bearerParser;

    @Autowired
    public TokenParserFactoryImpl( ObjectHashParserFactory objectHashParserFactory,
                                   SignatureValidator signatureValidator,
                                   BearerParser bearerParser ) {
        this.objectHashParserFactory = objectHashParserFactory;
        this.signatureValidator = signatureValidator;
        this.bearerParser = bearerParser;
    }

    @Override
    public TokenParser build(String token) throws TokenParserException {
        return new TokenParserImpl( token, objectHashParserFactory, signatureValidator, bearerParser );
    }
}
