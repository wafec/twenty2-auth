package twenty2.auth.api.core;

import twenty2.auth.api.entities.Claim;
import twenty2.auth.api.entities.User;
import twenty2.auth.api.exceptions.ObjectHashGeneratorException;
import twenty2.auth.shared.dto.jwt.JwtHeaderDto;
import twenty2.auth.shared.dto.jwt.JwtPayloadDto;
import twenty2.auth.shared.exceptions.TokenGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TokenBuilderImpl implements TokenBuilder {
    private final SignatureGenerator signatureGenerator;
    private final ObjectHashGeneratorFactory objectHashGeneratorFactory;
    private final String signatureAlgorithm;

    public static final String JWT_HEADER_TYPE = "jwt";
    public static final String TOKEN_BUILDER_SIGNATURE_ALGORITHM_DEFAULT = "RSA";
    public static final String TOKEN_BUILDER_SIGNATURE_ALGORITHM_PROPERTY = "${token-builder-signature-algorithm:" +
            TOKEN_BUILDER_SIGNATURE_ALGORITHM_DEFAULT + "}";

    @Autowired
    public TokenBuilderImpl( SignatureGenerator signatureGenerator,
                            ObjectHashGeneratorFactory objectHashGeneratorFactory,
                            @Value( TOKEN_BUILDER_SIGNATURE_ALGORITHM_PROPERTY ) String signatureAlgorithm ) {
        this.signatureGenerator = signatureGenerator;
        this.objectHashGeneratorFactory = objectHashGeneratorFactory;
        this.signatureAlgorithm = signatureAlgorithm;
    }

    private JwtHeaderDto newHeader() {
        JwtHeaderDto jwtHeaderDto = new JwtHeaderDto();

        jwtHeaderDto.setSignAlg( signatureAlgorithm );
        jwtHeaderDto.setHashAlg( objectHashGeneratorFactory.algorithm() );
        jwtHeaderDto.setType( JWT_HEADER_TYPE );

        return jwtHeaderDto;
    }

    private JwtPayloadDto newPayload( User user ) {
        JwtPayloadDto jwtPayloadDto = new JwtPayloadDto();

        jwtPayloadDto.setName( user.getName() );
        jwtPayloadDto.setClaims( Optional.ofNullable( user.getClaims() )
                .orElse( Collections.emptyList() )
                .stream().map( Claim::getValue ).collect( Collectors.toList() ) );

        return jwtPayloadDto;
    }

    @Override
    public String generate( User user ) throws TokenGenerationException {
        JwtHeaderDto jwtHeaderDto = newHeader();
        JwtPayloadDto jwtPayloadDto = newPayload( user );
        try {
            ObjectHashGenerator hashGeneratorHeaderDto = objectHashGeneratorFactory.build( newHeader() );
            ObjectHashGenerator hashGeneratorPayloadDto = objectHashGeneratorFactory.build( jwtPayloadDto );
            String digitalSignature = signatureGenerator.signObject( jwtHeaderDto.getSignAlg(), hashGeneratorPayloadDto );
            return String.format( "%s.%s.%s",
                    hashGeneratorHeaderDto.jsonBase64(),
                    hashGeneratorPayloadDto.jsonBase64(),
                    digitalSignature
            );
        } catch ( GeneralSecurityException | ObjectHashGeneratorException exc ) {
            throw new TokenGenerationException( exc );
        }
    }
}
