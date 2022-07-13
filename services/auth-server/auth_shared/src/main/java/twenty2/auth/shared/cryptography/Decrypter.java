package twenty2.auth.shared.cryptography;

import twenty2.auth.shared.exceptions.CryptographyException;

import java.security.PublicKey;

public interface Decrypter {
    byte[] decrypt( byte[] data, PublicKey publicKey, String algorithm ) throws CryptographyException;
}
