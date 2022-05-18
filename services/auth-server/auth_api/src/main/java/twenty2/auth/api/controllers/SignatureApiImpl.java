package twenty2.auth.api.controllers;

import twenty2.auth.shared.core.BearerParser;
import twenty2.auth.shared.core.PublicKeyManager;
import twenty2.auth.shared.api.SignatureApi;
import twenty2.auth.shared.core.TokenParserFactory;
import twenty2.auth.shared.dto.jwt.BearerDto;
import twenty2.auth.shared.dto.jwt.JwtPayloadDto;
import twenty2.auth.shared.exceptions.TokenParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/signature" )
public class SignatureApiImpl implements SignatureApi {
    private final PublicKeyManager publicKeyManager;
    private final TokenParserFactory tokenParserFactory;
    private final BearerParser bearerParser;

    @Autowired
    public SignatureApiImpl( PublicKeyManager publicKeyManager,
                             TokenParserFactory tokenParserFactory,
                             BearerParser bearerParser ) {
        this.publicKeyManager = publicKeyManager;
        this.tokenParserFactory = tokenParserFactory;
        this.bearerParser = bearerParser;
    }

    @Override
    @GetMapping( "/public-key" )
    public String getPublicKey() {
        return publicKeyManager.contentString();
    }

    @Override
    @GetMapping( "/validate" )
    @Secured( SIGNATURE_RESOURCE_READ_PERMISSION )
    public JwtPayloadDto validate( @RequestHeader( "Authorization" ) String authorization )
            throws TokenParserException {
        BearerDto bearerDto = bearerParser.parse( authorization );
        return tokenParserFactory.build( bearerDto.getContent() )
                .payload();
    }
}
