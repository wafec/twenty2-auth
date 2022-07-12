package twenty2.auth.api.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.util.Base64;

@Component
public class SignatureGeneratorImpl implements SignatureGenerator {
    private final PrivateKeyManager privateKeyManager;

    @Autowired
    public SignatureGeneratorImpl( PrivateKeyManager privateKeyManager ) {
        this.privateKeyManager = privateKeyManager;
    }

    @Override
    public String signObject( String alg, ObjectHashGenerator hashGenerator ) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance( alg );
        cipher.init( Cipher.ENCRYPT_MODE, privateKeyManager.privateKey() );
        return Base64.getEncoder().encodeToString( cipher.doFinal( hashGenerator.hash() ) )
                .replaceFirst( "=+$", "" );
    }
}
