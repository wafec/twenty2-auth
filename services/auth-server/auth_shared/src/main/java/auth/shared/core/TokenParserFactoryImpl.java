package auth.shared.core;

import auth.shared.exceptions.TokenParserException;

public class TokenParserFactoryImpl implements TokenParserFactory {
    private final ObjectHashParserFactory objectHashParserFactory;
    private final SignatureValidator signatureValidator;

    public TokenParserFactoryImpl( ObjectHashParserFactory objectHashParserFactory,
                                   SignatureValidator signatureValidator ) {
        this.objectHashParserFactory = objectHashParserFactory;
        this.signatureValidator = signatureValidator;
    }

    @Override
    public TokenParser build(String token) throws TokenParserException {
        return new TokenParserImpl( token, objectHashParserFactory, signatureValidator );
    }
}
