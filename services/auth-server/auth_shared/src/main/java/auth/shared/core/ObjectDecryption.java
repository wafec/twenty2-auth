package auth.shared.core;

import auth.shared.exceptions.ObjectDecryptionException;

import java.security.GeneralSecurityException;

public interface ObjectDecryption {
    <T> T decrypt( String encryptedMessage, Class<T> valueType )
            throws GeneralSecurityException, ObjectDecryptionException;
}
