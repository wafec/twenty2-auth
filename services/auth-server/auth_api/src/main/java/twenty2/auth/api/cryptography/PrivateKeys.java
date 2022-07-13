package twenty2.auth.api.cryptography;

import twenty2.auth.api.exceptions.CryptographyException;

import java.security.PrivateKey;

public interface PrivateKeys {
    PrivateKey fromPemFile( String privateKeyPemFile, String privateKeyAlgorithm ) throws CryptographyException;
}
