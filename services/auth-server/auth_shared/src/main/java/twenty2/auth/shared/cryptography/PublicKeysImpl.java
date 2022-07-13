package twenty2.auth.shared.cryptography;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Component;
import twenty2.auth.shared.exceptions.CryptographyException;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

@Component
public class PublicKeysImpl implements PublicKeys {
    @Override
    public PublicKey fromPemFile( String publicKeyPemFile, String publicKeyAlgorithm )
            throws CryptographyException {
        try( FileReader fileReader = new FileReader( publicKeyPemFile ) ) {
            KeyFactory keyFactory = KeyFactory.getInstance( publicKeyAlgorithm );
            PemReader pemReader = new PemReader( fileReader );
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec( content );
            return keyFactory.generatePublic( publicKeySpec );
        } catch( GeneralSecurityException | IOException exc ) {
            throw new CryptographyException( exc );
        }
    }
}
