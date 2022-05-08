package auth.shared.core;

import auth.shared.dto.jwt.BearerDto;
import auth.shared.dto.jwt.JwtHeaderDto;
import auth.shared.dto.jwt.JwtPayloadDto;
import auth.shared.exceptions.ObjectHashParserException;
import auth.shared.exceptions.SignatureValidatorException;
import auth.shared.exceptions.TokenParserException;

public class TokenParserImpl implements TokenParser {
    private final String token;
    private final ObjectHashParserFactory objectHashParserFactory;
    private final SignatureValidator signatureValidator;
    private final BearerParser bearerParser;

    private JwtHeaderDto jwtHeaderDto;
    private JwtPayloadDto jwtPayloadDto;

    public TokenParserImpl( String token,
                            ObjectHashParserFactory objectHashParserFactory,
                            SignatureValidator signatureValidator,
                            BearerParser bearerParser )
            throws TokenParserException {
        this.token = token;
        this.objectHashParserFactory = objectHashParserFactory;
        this.signatureValidator = signatureValidator;
        this.bearerParser = bearerParser;
        initializeComponent();
    }

    private void initializeComponent() throws TokenParserException {
        BearerDto bearerDto = bearerParser.parse( token );
        try {
            ObjectHashParser<JwtHeaderDto> jwtHeader = objectHashParserFactory
                    .build( JwtHeaderDto.class, bearerDto.getHeaderString() );
            ObjectHashParser<JwtPayloadDto> jwtPayload = objectHashParserFactory
                    .build( JwtPayloadDto.class, bearerDto.getPayloadString() );
            if (! signatureValidator.validate( jwtHeader.getInstance(), jwtPayload,
                    bearerDto.getDigitalSignatureString() ) ) {
                throw new TokenParserException();
            }
            jwtHeaderDto = jwtHeader.getInstance();
            jwtPayloadDto = jwtPayload.getInstance();
        } catch ( ObjectHashParserException | SignatureValidatorException exc ) {
            throw new TokenParserException( exc );
        }
    }

    @Override
    public JwtHeaderDto header() {
        return jwtHeaderDto;
    }

    @Override
    public JwtPayloadDto payload() {
        return jwtPayloadDto;
    }
}
