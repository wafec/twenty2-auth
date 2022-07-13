package twenty2.auth.api.cryptography;

import org.springframework.stereotype.Component;
import twenty2.auth.api.exceptions.CryptographyException;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

@Component
public class EncrypterImpl implements Encrypter {
    @Override
    public byte[] encrypt( byte[] data, PrivateKey privateKey, String algorithm ) throws CryptographyException {
        try {
            Cipher cipher = Cipher.getInstance( algorithm );
            cipher.init( Cipher.ENCRYPT_MODE, privateKey );
            return cipher.doFinal( data );
        } catch ( GeneralSecurityException exc ) {
            throw new CryptographyException( exc );
        }
    }
}
