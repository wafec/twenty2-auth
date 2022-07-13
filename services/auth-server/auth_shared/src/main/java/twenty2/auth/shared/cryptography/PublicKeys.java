package twenty2.auth.shared.cryptography;

import twenty2.auth.shared.exceptions.CryptographyException;

import java.security.PublicKey;

public interface PublicKeys {
    PublicKey fromPemFile(String publicKeyPemFile, String publicKeyAlgorithm ) throws CryptographyException;
}
