package auth.core;

import auth.entities.User;
import auth.exceptions.ObjectHashGeneratorException;
import auth.shared.dto.jwt.JwtHeaderDto;
import auth.shared.dto.jwt.JwtPayloadDto;
import auth.shared.exceptions.TokenGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;

@Component
public class TokenBuilderImpl implements TokenBuilder {
    private final SignatureGenerator signatureGenerator;
    private final ObjectHashGeneratorFactory objectHashGeneratorFactory;
    private final String signatureAlgorithm;

    public static final String JWT_HEADER_TYPE = "jwt";

    @Autowired
    public TokenBuilderImpl( SignatureGenerator signatureGenerator,
                            ObjectHashGeneratorFactory objectHashGeneratorFactory,
                            @Value( "${token-builder-signature-algorithm:RSA}" ) String signatureAlgorithm ) {
        this.signatureGenerator = signatureGenerator;
        this.objectHashGeneratorFactory = objectHashGeneratorFactory;
        this.signatureAlgorithm = signatureAlgorithm;
    }

    @Override
    public String generate( User user ) throws TokenGenerationException {
        JwtHeaderDto jwtHeaderDto = new JwtHeaderDto();
        JwtPayloadDto jwtPayloadDto = new JwtPayloadDto();
        try {
            ObjectHashGenerator hashGeneratorHeaderDto = objectHashGeneratorFactory.build( jwtHeaderDto );
            ObjectHashGenerator hashGeneratorPayloadDto = objectHashGeneratorFactory.build( jwtPayloadDto );
            jwtHeaderDto.setSignAlg( signatureAlgorithm );
            jwtHeaderDto.setHashAlg( objectHashGeneratorFactory.algorithm() );
            jwtHeaderDto.setType( JWT_HEADER_TYPE );
            jwtPayloadDto.setName( user.getName() );
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
