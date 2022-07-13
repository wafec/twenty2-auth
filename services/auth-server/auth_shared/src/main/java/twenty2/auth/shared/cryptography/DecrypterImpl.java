package twenty2.auth.shared.cryptography;

import org.springframework.stereotype.Component;
import twenty2.auth.shared.exceptions.CryptographyException;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.PublicKey;

@Component
public class DecrypterImpl implements Decrypter {
    @Override
    public byte[] decrypt( byte[] data, PublicKey publicKey, String algorithm) throws CryptographyException {
        try {
            Cipher cipher = Cipher.getInstance( algorithm );
            cipher.init( Cipher.DECRYPT_MODE, publicKey );
            return cipher.doFinal( data );
        } catch( GeneralSecurityException exc ) {
            throw new CryptographyException( exc );
        }
    }
}
