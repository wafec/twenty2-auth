package twenty2.auth.shared.core;

import twenty2.auth.shared.dto.jwt.JwtHeaderDto;
import twenty2.auth.shared.dto.jwt.JwtPayloadDto;
import twenty2.auth.shared.exceptions.ObjectHashParserException;
import twenty2.auth.shared.exceptions.SignatureValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

@Component
public class SignatureValidatorImpl implements SignatureValidator {
    private final PublicKeyManager publicKeyManager;

    @Autowired
    public SignatureValidatorImpl( PublicKeyManager publicKeyManager ) {
        this.publicKeyManager = publicKeyManager;
    }

    @Override
    public Boolean validate(JwtHeaderDto headerDto, ObjectHashParser<JwtPayloadDto> payloadHashParser,
                            String signature) throws SignatureValidatorException {
        try {
            Cipher cipher = Cipher.getInstance( headerDto.getSignAlg() );
            cipher.init( Cipher.DECRYPT_MODE, publicKeyManager.publicKey() );
            byte[] hash = cipher.doFinal( Base64.getDecoder().decode(signature) );
            return Arrays.equals( hash, payloadHashParser.hash( headerDto.getHashAlg() ) );
        } catch( GeneralSecurityException | ObjectHashParserException exc ) {
            throw new SignatureValidatorException( exc );
        }
    }
}
