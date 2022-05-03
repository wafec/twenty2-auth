package auth.core;

import java.security.PrivateKey;

public interface PrivateKeyManager {
    String contentString();

    PrivateKey privateKey();
}
