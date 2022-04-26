package auth.shared.core;

import java.security.GeneralSecurityException;

public interface ObjectDecryption {
    <T> T decrypt( String encryptedMessage ) throws GeneralSecurityException;
}
