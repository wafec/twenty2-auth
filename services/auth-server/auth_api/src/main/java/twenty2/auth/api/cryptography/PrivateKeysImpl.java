package twenty2.auth.api.cryptography;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Component;
import twenty2.auth.api.exceptions.CryptographyException;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

@Component
public class PrivateKeysImpl implements PrivateKeys {
    @Override
    public PrivateKey fromPemFile( String privateKeyPemFile, String privateKeyAlgorithm ) throws CryptographyException {
        try(FileReader fileReader = new FileReader( privateKeyPemFile ) ) {
            KeyFactory keyFactory = KeyFactory.getInstance( privateKeyAlgorithm );
            PemReader pemReader = new PemReader( fileReader );
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec( content );
            return keyFactory.generatePrivate( privateKeySpec );
        } catch ( GeneralSecurityException | IOException exc ) {
            throw new CryptographyException( exc );
        }
    }
}
