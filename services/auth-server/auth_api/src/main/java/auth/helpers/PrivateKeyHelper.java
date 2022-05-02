package auth.helpers;

import auth.exceptions.InvalidPrivateKeyException;

public interface PrivateKeyHelper {
    String readPrivateKeyFromPemFile( String privateKeyPemFile ) throws InvalidPrivateKeyException;
}
