package twenty2.auth.api.cryptography;

import twenty2.auth.api.exceptions.CryptographyException;

import java.security.PrivateKey;

public interface Encrypter {
    byte[] encrypt( byte[] data, PrivateKey privateKey, String algorithm ) throws CryptographyException;
}
