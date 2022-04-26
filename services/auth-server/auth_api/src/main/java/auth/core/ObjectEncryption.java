package auth.core;

import java.security.GeneralSecurityException;

public interface ObjectEncryption {
    String encrypt( Object obj ) throws GeneralSecurityException;
}
