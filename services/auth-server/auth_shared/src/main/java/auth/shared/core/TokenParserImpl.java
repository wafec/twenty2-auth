package auth.shared.core;

import auth.shared.dto.jwt.JwtHeaderDto;
import auth.shared.dto.jwt.JwtPayloadDto;
import auth.shared.exceptions.ObjectHashParserException;
import auth.shared.exceptions.SignatureValidatorException;
import auth.shared.exceptions.TokenParserException;

public class TokenParserImpl implements TokenParser {
    private final String token;
    private final ObjectHashParserFactory objectHashParserFactory;
    private final SignatureValidator signatureValidator;
    private JwtHeaderDto jwtHeaderDto;
    private JwtPayloadDto jwtPayloadDto;

    public TokenParserImpl( String token, ObjectHashParserFactory objectHashParserFactory,
                            SignatureValidator signatureValidator )
            throws TokenParserException {
        this.token = token;
        this.objectHashParserFactory = objectHashParserFactory;
        this.signatureValidator = signatureValidator;
        initializeComponent();
    }

    private void initializeComponent() throws TokenParserException {
        String[] sections = token.split("\\.");
        String headerSection = sections[0];
        String payloadSection = sections[1];
        String digitalSignatureSection = sections[2];
        try {
            ObjectHashParser<JwtHeaderDto> jwtHeader = objectHashParserFactory
                    .build( JwtHeaderDto.class, headerSection );
            ObjectHashParser<JwtPayloadDto> jwtPayload = objectHashParserFactory
                    .build( JwtPayloadDto.class, payloadSection );
            if (! signatureValidator.validate( jwtHeader.getInstance(), jwtPayload, digitalSignatureSection ) ) {
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
