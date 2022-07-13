package twenty2.auth.api.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twenty2.auth.api.cryptography.Encrypter;
import twenty2.auth.api.exceptions.CryptographyException;

import java.util.Base64;

@Component
public class SignatureGeneratorImpl implements SignatureGenerator {
    private final PrivateKeyManager privateKeyManager;
    private final Encrypter encrypter;

    @Autowired
    public SignatureGeneratorImpl( PrivateKeyManager privateKeyManager, Encrypter encrypter ) {
        this.privateKeyManager = privateKeyManager;
        this.encrypter = encrypter;
    }

    @Override
    public String signObject( String alg, ObjectHashGenerator hashGenerator ) throws CryptographyException {
        return Base64.getEncoder().encodeToString(
                encrypter.encrypt( hashGenerator.hash(), privateKeyManager.privateKey(), alg ) )
                    .replaceFirst( "=+$", "" );
    }
}
