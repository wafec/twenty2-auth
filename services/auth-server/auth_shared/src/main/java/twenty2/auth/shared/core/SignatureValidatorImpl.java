package twenty2.auth.shared.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twenty2.auth.shared.cryptography.Decrypter;
import twenty2.auth.shared.dto.jwt.JwtHeaderDto;
import twenty2.auth.shared.dto.jwt.JwtPayloadDto;
import twenty2.auth.shared.exceptions.CryptographyException;
import twenty2.auth.shared.exceptions.ObjectHashParserException;
import twenty2.auth.shared.exceptions.SignatureValidatorException;

import java.util.Arrays;
import java.util.Base64;

@Component
public class SignatureValidatorImpl implements SignatureValidator {
    private final PublicKeyManager publicKeyManager;
    private final Decrypter decrypter;

    @Autowired
    public SignatureValidatorImpl(
            PublicKeyManager publicKeyManager,
            Decrypter decrypter) {
        this.publicKeyManager = publicKeyManager;
        this.decrypter = decrypter;
    }

    @Override
    public Boolean validate( JwtHeaderDto headerDto, ObjectHashParser<JwtPayloadDto> payloadHashParser,
                             String signature ) throws SignatureValidatorException {
        try {
            byte[] hash = decrypter.decrypt( Base64.getDecoder().decode( signature ), publicKeyManager.publicKey(),
                    headerDto.getSignAlg() );
            return Arrays.equals( hash, payloadHashParser.hash( headerDto.getHashAlg() ) );
        } catch( CryptographyException | ObjectHashParserException exc ) {
            throw new SignatureValidatorException( exc );
        }
    }
}
